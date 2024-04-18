package domain.model.dashboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDashboard(

	@SerialName("code")
	val code: Int? = null,

	@SerialName("data")
	val data: DataDashboard? = null,

	@SerialName("success")
	val success: Boolean? = null,

	@SerialName("message")
	val message: String? = null
)

@Serializable
data class Roles(

	@SerialName("updated_at")
	val updatedAt: String? = null,

	@SerialName("updated_by")
	val updatedBy: String? = null,

	@SerialName("created_at")
	val createdAt: String? = null,

	@SerialName("role_title")
	val roleTitle: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("created_by")
	val createdBy: String? = null,

	@SerialName("deleted_at")
	val deletedAt: String? = null
)

@Serializable
data class User(

	@SerialName("roles")
	val roles: Roles? = null,

	@SerialName("created_at")
	val createdAt: String? = null,

	@SerialName("email_verified_at")
	val emailVerifiedAt: String? = null,

	@SerialName("employee")
	val employee: Employee? = null,

	@SerialName("created_by")
	val createdBy: String? = null,

	@SerialName("deleted_at")
	val deletedAt: String? = null,

	@SerialName("updated_at")
	val updatedAt: String? = null,

	@SerialName("role_id")
	val roleId: Int? = null,

	@SerialName("employee_id")
	val employeeId: Int? = null,

	@SerialName("updated_by")
	val updatedBy: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("email")
	val email: String? = null,

	@SerialName("username")
	val username: String? = null
)

@Serializable
data class Employee(

	@SerialName("quota_leave")
	val quotaLeave: Int? = null,

	@SerialName("url_avatar")
	val urlAvatar: String? = null,

	@SerialName("address")
	val address: String? = null,

	@SerialName("contract_id")
	val contractId: Int? = null,

	@SerialName("join_date")
	val joinDate: String? = null,

	@SerialName("birth_date")
	val birthDate: String? = null,

	@SerialName("created_at")
	val createdAt: String? = null,

	@SerialName("salary")
	val salary: Int? = null,

	@SerialName("created_by")
	val createdBy: String? = null,

	@SerialName("deleted_at")
	val deletedAt: String? = null,

	@SerialName("remaining_days_off")
	val remainingDaysOff: Int? = null,

	@SerialName("nik")
	val nik: Long? = null,

	@SerialName("full_name")
	val fullName: String? = null,

	@SerialName("updated_at")
	val updatedAt: String? = null,

	@SerialName("updated_by")
	val updatedBy: String? = null,

	@SerialName("birth_location")
	val birthLocation: String? = null,

	@SerialName("phone_number")
	val phoneNumber: String? = null,

	@SerialName("id")
	val id: Int? = null,

	@SerialName("employee_status")
	val employeeStatus: String? = null,

	@SerialName("exist_location")
	val existLocation: String? = null,

	@SerialName("position_id")
	val positionId: Int? = null
)

@Serializable
data class DataDashboard(

	@SerialName("dashboardData")
	val dashboardData: String? = null,

	@SerialName("user")
	val user: User? = null
)
