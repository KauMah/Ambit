//
//  SettingsViewController.swift
//  FireChat-Swift
//
//  Created by Tim Lupo on 9/5/15.
//  Copyright (c) 2015 Firebase. All rights reserved.
//

import Foundation

class SettingsViewController: UIViewController {
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        title = "Settings"
    }
    
    
    
    /* Switch usernames
    
    @IBOutlet weak var nameField: UITextField!
    var username: String!
    
    override func viewDidAppear(animated: Bool) {
        nameField.attributedPlaceholder = NSAttributedString(string: AmbitAuthHelper.sharedInstance.username!)
    }

    @IBAction func editUsername(sender: UITextField) {
        username = sender.text
    }
    
    @IBAction func saveExit(sender: UIButton) {
        if (username != nil && username  != "") {
            AmbitAuthHelper.sharedInstance.setName(username!)
            self.performSegueWithIdentifier("USER_SETTINGS", sender: "@ambit")
        } else {
            AmbitAuthHelper.sharedInstance.setName("Anonymous")
            self.performSegueWithIdentifier("USER_SETTINGS", sender: "@ambit")
        }
    }
    */
}
