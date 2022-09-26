package com.xy.android

import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


/**
 * 联系人相关
 * 添加/更新/删除联系人
 */
class ContactActivity : AppCompatActivity() {

    /**
     * 联系人信息
     * @param rawContactId 联系人raw_contact_id
     * @param name 名字
     * @param data 其它数据
     */
    data class ContactData(
        val rawContactId: Long,
        val name: String,
        val data: ArrayList<ContentValues>
    )

    /**
     * 当前联系人信息
     */
    private var currentContactData: ContactData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        findViewById<Button>(R.id.btnAddContactByIntent).setOnClickListener {
            addContactByIntent()
        }

        findViewById<Button>(R.id.btnUpdateContactByIntent).setOnClickListener {
            updateContactByIntent()
        }

        findViewById<Button>(R.id.btnAddContactDirectly).setOnClickListener {
            addContactDirectly()
        }

        findViewById<Button>(R.id.btnUpdateContactDirectly).setOnClickListener {
            updateContactDirectly()
        }

        findViewById<Button>(R.id.btnDeleteContactDirectly).setOnClickListener {
            deleteContactDirectly()
        }

        findViewById<Button>(R.id.btnQueryContact).setOnClickListener {
            queryContact()
        }
    }

    /**
     * 跳转系统联系人界面添加新的联系人
     */
    private fun addContactByIntent() {
        val intent = Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI)
        // 先插入一条空的ContentValues()，用以获取raw_contact_id
        val rawContactUri =
            contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, ContentValues())
                ?: return
        val rawContactId = ContentUris.parseId(rawContactUri)
        currentContactData = getContactData(rawContactId).apply {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, name)
            intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data)
        }
        try {
            startActivity(intent)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * 跳转系统联系人界面更新现有联系人
     */
    private fun updateContactByIntent() {
        val intent = Intent(Intent.ACTION_INSERT_OR_EDIT)
            .setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE)
        val rawContactId = currentContactData?.rawContactId
        if (rawContactId == null) {
            Toast.makeText(this, "无当前联系人信息，请先创建联系人", Toast.LENGTH_SHORT).show()
            return
        }
        currentContactData = getContactData(rawContactId).apply {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, name)
            intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data)
        }
        try {
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * 直接添加新的联系人
     * 需要android.permission.WRITE_CONTACTS权限
     */
    private fun addContactDirectly() {
        // 先插入一条空的ContentValues()，用以获取raw_contact_id
        val rawContactUri =
            contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, ContentValues())
                ?: return
        val rawContactId = ContentUris.parseId(rawContactUri)
        val contactData = getContactData(rawContactId)
        currentContactData = contactData
        contentResolver.bulkInsert(
            ContactsContract.Data.CONTENT_URI,
            contactData.data.toTypedArray()
        )
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show()
    }

    /**
     * 直接更新现有联系人
     * 需要android.permission.WRITE_CONTACTS权限
     */
    private fun updateContactDirectly() {
        val rawContactId = currentContactData?.rawContactId
        if (rawContactId == null) {
            Toast.makeText(this, "无当前联系人信息，请先创建联系人", Toast.LENGTH_SHORT).show()
            return
        }

        // 名字
        val nameValues = generateContentValues(rawContactId)
        nameValues.put(
            ContactsContract.CommonDataKinds.StructuredName.MIMETYPE,
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
        )
        val random = Random.nextInt(1000)
        val givenName = "given$random"
        nameValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, givenName)
        val middleName = "middle$random"
        nameValues.put(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, middleName)
        val familyName = "family$random"
        nameValues.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, familyName)
        val name = "$givenName $middleName $familyName"
        nameValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)

        contentResolver.update(
            ContactsContract.Data.CONTENT_URI,
            nameValues,
            "${ContactsContract.Data.RAW_CONTACT_ID}=? and ${ContactsContract.Data.MIMETYPE}=?",
            arrayOf(
                rawContactId.toString(),
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
            )
        )
        Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show()
    }

    /**
     * 删除联系人
     * 需要android.permission.WRITE_CONTACTS权限
     */
    private fun deleteContactDirectly() {
        val rawContactId = currentContactData?.rawContactId
        if (rawContactId == null) {
            Toast.makeText(this, "无当前联系人信息，请先创建联系人", Toast.LENGTH_SHORT).show()
            return
        }
        contentResolver.delete(
            ContactsContract.Data.CONTENT_URI, "${ContactsContract.Data.RAW_CONTACT_ID}=?",
            arrayOf(rawContactId.toString())
        )
        currentContactData = null
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show()
    }

    /**
     * 查找联系人
     * 需要android.permission.READ_CONTACTS权限
     */
    private fun queryContact() {
        val rawContactId = currentContactData?.rawContactId
        if (rawContactId == null) {
            Toast.makeText(this, "无当前联系人信息，请先创建联系人", Toast.LENGTH_SHORT).show()
            return
        }
        val cursor = contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            "${ContactsContract.Data.RAW_CONTACT_ID}=?",
            arrayOf(rawContactId.toString()),
            null
        )
        if (cursor == null) {
            Toast.makeText(this, "查找联系人失败", Toast.LENGTH_SHORT).show()
        } else {
            cursor.moveToFirst()
            val displayName = cursor.getString(76)
            Toast.makeText(this, "查找联系人成功，displayName: $displayName", Toast.LENGTH_SHORT)
                .show()
        }
        cursor?.close()
    }

    /**
     * 获取联系人数据
     */
    private fun getContactData(rawContactId: Long): ContactData {
        val data = ArrayList<ContentValues>()

        // 名字
        val nameValues = generateContentValues(rawContactId)
        nameValues.put(
            ContactsContract.CommonDataKinds.StructuredName.MIMETYPE,
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
        )
        val givenName = "given"
        nameValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, givenName)
        val middleName = "middle"
        nameValues.put(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, middleName)
        val familyName = "family"
        nameValues.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, familyName)
        val name = "$givenName $middleName $familyName"
        nameValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
        data.add(nameValues)

        // 昵称
        val nickNameValues = generateContentValues(rawContactId)
        nickNameValues.put(
            ContactsContract.CommonDataKinds.Nickname.MIMETYPE,
            ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE
        )
        val nickName = "nickName"
        nickNameValues.put(ContactsContract.CommonDataKinds.Nickname.NAME, nickName)
        data.add(nickNameValues)

        // 头像
        val photoValues = generateContentValues(rawContactId)
        photoValues.put(
            ContactsContract.CommonDataKinds.Photo.MIMETYPE,
            ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
        )
        photoValues.put(
            ContactsContract.CommonDataKinds.Photo.PHOTO,
            try {
                assets.open("photo.jpg")
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }?.readBytes()
        )
        data.add(photoValues)

        // 备注
        val remarkValues = generateContentValues(rawContactId)
        remarkValues.put(
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE
        )
        val remark = "your remark"
        remarkValues.put(ContactsContract.CommonDataKinds.Note.NOTE, remark)
        data.add(remarkValues)

        // 电话号码
        val mobilePhoneNumberValues = generateContentValues(rawContactId)
        mobilePhoneNumberValues.put(
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )
        mobilePhoneNumberValues.put(
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
        )
        val mobilePhoneNumber = "13999999999"
        mobilePhoneNumberValues.put(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            mobilePhoneNumber
        )
        data.add(mobilePhoneNumberValues)

        // 工作传真号码
        val workFaxNumberValues = generateContentValues(rawContactId)
        workFaxNumberValues.put(
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )
        workFaxNumberValues.put(
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK
        )
        val workFaxNumber = "66669999999"
        workFaxNumberValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, workFaxNumber)
        data.add(workFaxNumberValues)

        // 工作电话号码
        val workPhoneNumberValues = generateContentValues(rawContactId)
        workPhoneNumberValues.put(
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )
        workPhoneNumberValues.put(
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE_WORK
        )
        val workPhoneNumber = "6666666666"
        workPhoneNumberValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, workPhoneNumber)
        data.add(workPhoneNumberValues)

        // 公司电话号码
        val hostNumberValues = generateContentValues(rawContactId)
        hostNumberValues.put(
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )
        hostNumberValues.put(
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN
        )
        val hostNumber = "99999999999"
        hostNumberValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, hostNumber)
        data.add(hostNumberValues)

        // 住宅传真号码
        val homeFaxNumberValues = generateContentValues(rawContactId)
        homeFaxNumberValues.put(
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )
        homeFaxNumberValues.put(
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME
        )
        val homeFaxNumber = "99996666666"
        homeFaxNumberValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, homeFaxNumber)
        data.add(homeFaxNumberValues)

        // 住宅电话号码
        val homePhoneNumberValues = generateContentValues(rawContactId)
        homePhoneNumberValues.put(
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )
        homePhoneNumberValues.put(
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE_HOME
        )
        val homePhoneNumber = "99998888888"
        homePhoneNumberValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, homePhoneNumber)
        data.add(homePhoneNumberValues)

        // 微信号
        val weChatNumberValues = generateContentValues(rawContactId)
        weChatNumberValues.put(
            ContactsContract.CommonDataKinds.Im.MIMETYPE,
            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE
        )
        weChatNumberValues.put(
            ContactsContract.CommonDataKinds.Im.PROTOCOL,
            ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM
        )
        weChatNumberValues.put(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL, "WeChat")
        val weChatNumber = "wx66666666"
        weChatNumberValues.put(ContactsContract.CommonDataKinds.Im.DATA, weChatNumber)
        data.add(weChatNumberValues)

        // 地址
        val addressValues = generateContentValues(rawContactId)
        addressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.MIMETYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE
        )
        addressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER
        )
        val addressCountry = "addressCountry"
        addressValues.put(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY, addressCountry)
        val addressState = "addressState"
        addressValues.put(ContactsContract.CommonDataKinds.StructuredPostal.REGION, addressState)
        val addressCity = "addressCity"
        addressValues.put(ContactsContract.CommonDataKinds.StructuredPostal.CITY, addressCity)
        val addressStreet = "addressStreet"
        addressValues.put(ContactsContract.CommonDataKinds.StructuredPostal.STREET, addressStreet)
        val addressPostalCode = "addressPostalCode"
        addressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE,
            addressPostalCode
        )
        addressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
            "$addressStreet $addressCity $addressState $addressCountry $addressPostalCode"
        )
        data.add(addressValues)

        // 工作地址
        val workAddressValues = generateContentValues(rawContactId)
        workAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.MIMETYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE
        )
        workAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK
        )
        val workAddressCountry = "workAddressCountry"
        workAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY,
            workAddressCountry
        )
        val workAddressState = "workAddressState"
        workAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.REGION,
            workAddressState
        )
        val workAddressCity = "workAddressCity"
        workAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.CITY,
            workAddressCity
        )
        val workAddressStreet = "workAddressStreet"
        workAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.STREET,
            workAddressStreet
        )
        val workAddressPostalCode = "workAddressPostalCode"
        workAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE,
            workAddressPostalCode
        )
        workAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
            "$workAddressStreet $workAddressCity $workAddressState $workAddressCountry $workAddressPostalCode"
        )
        data.add(workAddressValues)

        // 住宅地址
        val homeAddressValues = generateContentValues(rawContactId)
        homeAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.MIMETYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE
        )
        homeAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME
        )
        val homeAddressCountry = "homeAddressCountry"
        homeAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY,
            homeAddressCountry
        )
        val homeAddressState = "homeAddressState"
        homeAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.REGION,
            homeAddressState
        )
        val homeAddressCity = "homeAddressCity"
        homeAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.CITY,
            homeAddressCity
        )
        val homeAddressStreet = "homeAddressStreet"
        homeAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.STREET,
            homeAddressStreet
        )
        val homeAddressPostalCode = "homeAddressPostalCode"
        homeAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE,
            homeAddressPostalCode
        )
        homeAddressValues.put(
            ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
            "$homeAddressStreet $homeAddressCity $homeAddressState $homeAddressCountry $homeAddressPostalCode"
        )
        data.add(homeAddressValues)

        // 公司及职位
        val organizationValues = generateContentValues(rawContactId)
        organizationValues.put(
            ContactsContract.CommonDataKinds.Organization.MIMETYPE,
            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
        )
        organizationValues.put(
            ContactsContract.CommonDataKinds.Organization.TYPE,
            ContactsContract.CommonDataKinds.Organization.TYPE_WORK
        )
        val organization = "organization"
        organizationValues.put(ContactsContract.CommonDataKinds.Organization.COMPANY, organization)
        val title = "title"
        organizationValues.put(ContactsContract.CommonDataKinds.Organization.TITLE, title)
        data.add(organizationValues)

        // 邮箱
        val emailValues = generateContentValues(rawContactId)
        emailValues.put(
            ContactsContract.CommonDataKinds.Email.MIMETYPE,
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
        )
        val email = "yuriyshea@163.com"
        emailValues.put(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
        data.add(emailValues)

        // 网站
        val urlValues = generateContentValues(rawContactId)
        urlValues.put(
            ContactsContract.CommonDataKinds.Website.MIMETYPE,
            ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE
        )
        val url = "https://yuriyshea.com"
        urlValues.put(ContactsContract.CommonDataKinds.Website.URL, url)
        data.add(urlValues)

        return ContactData(rawContactId, name, data)
    }

    private fun generateContentValues(rawContactId: Long): ContentValues {
        return ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
        }
    }
}