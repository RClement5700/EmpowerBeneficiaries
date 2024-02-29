package com.clementcorporation.empowerbeneficiaries.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.clementcorporation.empowerbeneficiaries.R
import com.clementcorporation.empowerbeneficiaries.domain.Beneficiary
import com.clementcorporation.empowerbeneficiaries.domain.BeneficiaryAddress
import com.clementcorporation.empowerbeneficiaries.domain.BeneficiaryUiModel
import com.clementcorporation.empowerbeneficiaries.domain.toUiModel
import com.clementcorporation.empowerbeneficiaries.util.Constants.ADDRESS_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.ADDRESS_FIRST_LINE_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.ADDRESS_SECOND_LINE_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.ADDRESS_STATE_CODE_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.ADDRESS_ZIP_CODE_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.BENEFICIARIES_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.BENEFIT_TYPE_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.CITY_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.COUNTRY_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.DESIGNATION_CODE_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.DOB_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.FIRST_NAME_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.LAST_NAME_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.MIDDLE_NAME_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.PHONE_NUMBER_ENDPOINT
import com.clementcorporation.empowerbeneficiaries.util.Constants.SSN_ENDPOINT
import org.json.JSONArray
import org.json.JSONObject

private const val TAG = "MainFragment"
class MainFragment : Fragment() {

    private lateinit var beneficiaries: List<BeneficiaryUiModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        //retrieve data and map it into UI format
        super.onCreate(savedInstanceState)
        val json = readJSONFromAsset()
        val dataSetAsJson = JSONArray(json)
        val dataSet = arrayListOf<JSONObject>()
        for (i in 0..< dataSetAsJson.length()) {
            val obj = dataSetAsJson.getJSONObject(i)
            dataSet.add(obj)
        }
        beneficiaries = dataSet.map {
            val beneficiaryAddress = it.getJSONObject(ADDRESS_ENDPOINT)
            Beneficiary(
                beneType = it.getString(BENEFIT_TYPE_ENDPOINT),
                beneficiaryAddress = BeneficiaryAddress(
                    city = beneficiaryAddress.getString(CITY_ENDPOINT),
                    country = beneficiaryAddress.getString(COUNTRY_ENDPOINT),
                    firstLineMailing = beneficiaryAddress.getString(ADDRESS_FIRST_LINE_ENDPOINT),
                    scndLineMailing = beneficiaryAddress.getString(ADDRESS_SECOND_LINE_ENDPOINT),
                    stateCode = beneficiaryAddress.getString(ADDRESS_STATE_CODE_ENDPOINT),
                    zipCode = beneficiaryAddress.getString(ADDRESS_ZIP_CODE_ENDPOINT),
                ),
                dateOfBirth = it.getString(DOB_ENDPOINT),
                designationCode = it.getString(DESIGNATION_CODE_ENDPOINT),
                firstName = it.getString(FIRST_NAME_ENDPOINT),
                lastName = it.getString(LAST_NAME_ENDPOINT),
                middleName = it.getString(MIDDLE_NAME_ENDPOINT),
                phoneNumber = it.getString(PHONE_NUMBER_ENDPOINT),
                socialSecurityNumber = it.getString(SSN_ENDPOINT)
            )
        }.map { it.toUiModel() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view  = inflater.inflate(R.layout.fragment_main, container, false)
        val rvBeneficiaries = view.findViewById<RecyclerView>(R.id.rv_beneficiaries)
        rvBeneficiaries.adapter = BeneficiariesAdapter(beneficiaries) { beneficiary ->
            activity?.supportFragmentManager?.beginTransaction()
                ?.addToBackStack(TAG)
                ?.replace(R.id.container, BeneficiaryFragment.newInstance(beneficiary))
                ?.commit()
        }
        return view
    }

    private fun readJSONFromAsset(): String {
        return requireActivity().assets.open(BENEFICIARIES_ENDPOINT).bufferedReader().use { it.readText() }
    }
}