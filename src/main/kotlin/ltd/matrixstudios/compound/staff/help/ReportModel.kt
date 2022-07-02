package ltd.matrixstudios.compound.staff.help

import java.util.*

data class ReportModel(
    val whoReported: UUID,
    val target: UUID,
    val at: Long,
    val reason: String
) {
}