@file:OptIn(
    androidx.compose.foundation.layout.ExperimentalLayoutApi::class,
)

package moe.ice.systemtweaks.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.highcapable.yukihookapi.YukiHookAPI
import moe.ice.systemtweaks.R
import moe.ice.systemtweaks.model.HookTarget

@Composable
fun StatusCard(enabledScopes: List<HookTarget>) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(R.string.module_status_title),
                style = MaterialTheme.typography.titleLarge,
            )
            StatusLine(
                label = stringResource(R.string.module_status_activation),
                value = if (YukiHookAPI.Status.isModuleActive) {
                    stringResource(R.string.module_status_active)
                } else {
                    stringResource(R.string.module_status_inactive)
                },
            )
            StatusLine(
                label = stringResource(R.string.module_status_required_scope),
            ) {
                ScopeTags(enabledScopes = enabledScopes)
            }
            StatusLine(
                label = stringResource(R.string.module_status_api_level),
                value = YukiHookAPI.Status.Executor.apiLevel.toString(),
            )
        }
    }
}

@Composable
private fun StatusLine(
    label: String,
    value: String,
) {
    StatusLine(label = label) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun StatusLine(
    label: String,
    valueContent: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
        )
        valueContent()
    }
}

@Composable
private fun ScopeTags(enabledScopes: List<HookTarget>) {
    if (enabledScopes.isEmpty()) {
        Text(
            text = stringResource(R.string.module_status_required_scope_empty),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        return
    }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        enabledScopes.forEach { scope ->
            ScopeTag(label = stringResource(scope.labelRes))
        }
    }
}

@Composable
private fun ScopeTag(label: String) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
        ),
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
