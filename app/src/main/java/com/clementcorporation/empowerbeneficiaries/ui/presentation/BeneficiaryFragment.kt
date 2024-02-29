package com.clementcorporation.empowerbeneficiaries.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.clementcorporation.empowerbeneficiaries.R
import com.clementcorporation.empowerbeneficiaries.domain.BeneficiaryUiModel

private const val BENEFICIARY = "beneficiary"

class BeneficiaryFragment : Fragment() {
    private var beneficiary: BeneficiaryUiModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //deserialize data from MainFragment
            beneficiary = it.getSerializable(BENEFICIARY, BeneficiaryUiModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beneficiary, container, false)
        view.findViewById<TextView>(R.id.tv_name).apply {
            text = beneficiary?.name
        }
        view.findViewById<TextView>(R.id.tv_dob).apply {
            text = beneficiary?.dateOfBirth
        }
        view.findViewById<TextView>(R.id.tv_ssn).apply {
            text = beneficiary?.socialSecurityNumber
        }
        view.findViewById<TextView>(R.id.tv_phone_number).apply {
            text = beneficiary?.phoneNumber
        }
        view.findViewById<TextView>(R.id.tv_address).apply {
            text = beneficiary?.beneficiaryAddress
        }
        view.findViewById<TextView>(R.id.tv_designation).apply {
            text = beneficiary?.designationCode
        }
        view.findViewById<TextView>(R.id.tv_benefit_type).apply {
            text = beneficiary?.beneType
        }
        return view
    }

    companion object {

        fun newInstance(beneficiary: BeneficiaryUiModel) =
            BeneficiaryFragment().apply {
                arguments = Bundle().apply {
                    //serialize data from MainFragment
                    putSerializable(BENEFICIARY, beneficiary)
                }
            }
    }
}