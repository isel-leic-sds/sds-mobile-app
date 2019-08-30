package com.isel.ps.sds.view.profile

import android.os.Bundle
import androidx.lifecycle.Observer
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity<ProfileViewModel>() {
    override fun defineViewModel(): Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun layoutToInflate(): Int = R.layout.activity_profile

    override fun doOnCreate(savedInstanceState: Bundle?) {
        viewModel.person.observe(this, Observer<Person> { p ->
            update(p)
        })

        viewModel.getPerson()
    }

    private fun update(person: Person) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            personPic.setImageDrawable(this.resources.getDrawable(R.drawable.profile, this.theme))
        } else {
            personPic.setImageDrawable(this.resources.getDrawable(R.drawable.profile))
        }
        personName.text = person.name
        personDateOfBirth.text = person.dateBirth.toString()
        personNif.text = person.nif.toString()
        personSosContactName.text = person.SosContact.name
        personSosContactPhoneNumber.text = person.SosContact.phoneNumber.toString()
    }
}