package com.mobdeve.s11.group5.shopfreemobileapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.ProfileDialogBinding
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.ProfileSettingsBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SettingsActivity : ComponentActivity () {
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var viewBinding : ProfileSettingsBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewBinding = ProfileSettingsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.psPhoneItem.setOnClickListener {
            //dialog
            showCustomDialogue(1)
        }

        viewBinding.psEmailItem.setOnClickListener {
            //dialog
            showCustomDialogue(2)
        }

        viewBinding.psPasswordItem.setOnClickListener {
            //dialog
            showCustomDialogue(3)
        }

        viewBinding.psLogout.setOnClickListener {
            var executorService: ExecutorService = Executors.newSingleThreadExecutor();

            executorService.execute {
                auth.signOut()

                //go back to the login page
            }
        }

        this.viewBinding.psBack.setOnClickListener {
            finish()
        }
    }

    private fun showCustomDialogue(origin: Int): Dialog {
        return this?.let {
            val builder = AlertDialog.Builder(it)
            // The view binding instance of the dialogue box's layout

            var profileDialog : ProfileDialogBinding = ProfileDialogBinding.inflate(it.layoutInflater)

            if (origin == 1) {
                profileDialog.pdTitle.text = "Change Phone Number"
            }
            if (origin == 2) {
                profileDialog.pdTitle.text = "Change Email Address"
            }
            if (origin == 3) {
                profileDialog.pdTitle.text = "Change Password"
            }

            Log.d("[MEDIA]", "Dialog accessed")

            with(builder){
                // Logic for insertion of a media item into the DB
                setPositiveButton("SAVE",
                    DialogInterface.OnClickListener { dialog, id ->
                        //firestore update
                        var executorService: ExecutorService = Executors.newSingleThreadExecutor();

                        executorService.execute {
                            //Update the database here
                            dbRef = Firebase.firestore
                            val user = auth.currentUser
                            if (user != null) {
                                if (origin == 1) {
                                    dbRef.collection(MyFirestoreReferences.USERS_COLLECTION)
                                        .document(user.uid).update( "phoneNum",
                                            profileDialog.pdUserInput.text.toString()
                                        )
                                }

                                if (origin == 2) {
                                    dbRef.collection(MyFirestoreReferences.USERS_COLLECTION)
                                        .document(user.uid)
                                        .update( "email", profileDialog.pdUserInput.text.toString())
                                }

                                if (origin == 3) {
                                    auth.currentUser?.updatePassword(profileDialog.pdUserInput.text.toString())
                                }
                            }
                        }
                    })
                // If the user cancels, nothing happens.
                setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
                setView(profileDialog.root)
                create()
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}