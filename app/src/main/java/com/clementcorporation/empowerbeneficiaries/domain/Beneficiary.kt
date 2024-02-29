package com.clementcorporation.empowerbeneficiaries.domain

import java.io.Serializable

//Constants specific to this data class
private const val DES_CODE_CONTINGENT = "Contingent"
private const val DES_CODE_PRIMARY = "Primary"
private const val DES_CODE_P = "P"
private const val DES_CODE_C = "C"
private val MM_RANGE = 0..1
private val DD_RANGE = 2..3
private val YYYY_RANGE = 4..7

//Content how it will arrive via JSON
data class Beneficiary(
    val beneType: String,
    val beneficiaryAddress: BeneficiaryAddress,
    val dateOfBirth: String,
    val designationCode: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val phoneNumber: String,
    val socialSecurityNumber: String
): Serializable

data class BeneficiaryAddress(
    val city: String,
    val country: String,
    val firstLineMailing: String,
    val scndLineMailing: String?,
    val stateCode: String,
    val zipCode: String
)

//Content that will be displayed
data class BeneficiaryUiModel(
    val beneType: String,
    val beneficiaryAddress: String,
    val dateOfBirth: String,
    val designationCode: String,
    val name: String,
    val phoneNumber: String,
    val socialSecurityNumber: String
): Serializable

//mapper function to circumvent needing to perform data transformations in Fragments
fun Beneficiary.toUiModel(): BeneficiaryUiModel {
    val month = dateOfBirth.substring(MM_RANGE)
    val day = dateOfBirth.substring(DD_RANGE)
    val year = dateOfBirth.substring(YYYY_RANGE)
    val birthdate = "$month/$day/$year"
    val address =
        if (beneficiaryAddress.scndLineMailing.isNullOrEmpty()) "${beneficiaryAddress.firstLineMailing}\n" +
                "${beneficiaryAddress.scndLineMailing}\n" +
                "${beneficiaryAddress.city}, ${beneficiaryAddress.stateCode} ${beneficiaryAddress.zipCode}"
        else "${beneficiaryAddress.firstLineMailing}\n" +
            "${beneficiaryAddress.city}, ${beneficiaryAddress.stateCode}  ${beneficiaryAddress.zipCode}"
    return BeneficiaryUiModel(
        name = "$firstName $lastName",
        beneType = beneType,
        designationCode =
        when (designationCode) {
            DES_CODE_C -> DES_CODE_CONTINGENT
            DES_CODE_P -> DES_CODE_PRIMARY
            else -> ""
        },
        phoneNumber = phoneNumber,
        socialSecurityNumber = socialSecurityNumber,
        dateOfBirth = birthdate,
        beneficiaryAddress = address
    )
}