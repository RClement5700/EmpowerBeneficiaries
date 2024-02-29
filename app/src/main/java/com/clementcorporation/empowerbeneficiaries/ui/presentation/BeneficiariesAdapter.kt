package com.clementcorporation.empowerbeneficiaries.ui.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.clementcorporation.empowerbeneficiaries.R
import com.clementcorporation.empowerbeneficiaries.domain.BeneficiaryUiModel

class BeneficiariesAdapter(private val dataSet: List<BeneficiaryUiModel>,
                           private val onClick: (beneficiary: BeneficiaryUiModel) -> Unit
): RecyclerView.Adapter<BeneficiaryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryViewHolder {
        val itemView = LayoutInflater.from(parent.rootView.context).inflate(
            R.layout.beneficiary_item_view, parent, false
        )
        return BeneficiaryViewHolder(itemView)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: BeneficiaryViewHolder, position: Int) {
        val beneficiary = dataSet[position]
        holder.name.text = beneficiary.name
        holder.benefitType.text = beneficiary.beneType
        holder.designation.text = beneficiary.designationCode
        holder.getItemView().setOnClickListener {
            //open details screen from Fragment
            onClick(beneficiary)
        }
    }
}

class BeneficiaryViewHolder(private val itemView: View
): RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.tv_name)
    val benefitType: TextView = itemView.findViewById(R.id.tv_benefit_type)
    val designation: TextView = itemView.findViewById(R.id.tv_designation)

    fun getItemView() = itemView
}