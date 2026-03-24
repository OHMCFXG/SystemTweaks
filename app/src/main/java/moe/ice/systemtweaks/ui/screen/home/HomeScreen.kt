@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class,
)

package moe.ice.systemtweaks.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import moe.ice.systemtweaks.R
import moe.ice.systemtweaks.model.SettingGroup

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onToggleChanged: (String, Boolean) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(text = "System Tweaks")
                        Text(
                            text = stringResource(R.string.home_subtitle),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    StatusCard(enabledScopes = uiState.enabledScopes)
                }

                uiState.sections.forEach { section ->
                    item {
                        GroupHeader(group = section.group)
                    }
                    items(section.toggles, key = { it.definition.key }) { toggle ->
                        ToggleCard(
                            toggle = toggle,
                            onCheckedChange = { enabled ->
                                onToggleChanged(toggle.definition.key, enabled)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupHeader(group: SettingGroup) {
    Column(
        modifier = Modifier.padding(top = 20.dp, bottom = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stringResource(group.titleRes),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(group.descriptionRes),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
