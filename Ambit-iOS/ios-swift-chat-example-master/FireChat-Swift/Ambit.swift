//
//  Ambit.swift
//  FireChat-Swift
//
//  Created by Tim Lupo on 9/4/15.
//  Copyright (c) 2015 Firebase. All rights reserved.
//

import Foundation

class AmbitAuthHelper: NSObject {
    
    static let sharedInstance = AmbitAuthHelper()
    let user = Firebase(url: "https://ambit.firebaseio.com/users").childByAutoId()
    var username: String! = "Anonymous"
    var latitude: Double! = 0.0
    var longitude: Double! = 0.0
    var radius: Double! = 0.5
    
    func loginUser (name: String!) {
        setName(name)
    }
    
    func logoutUser () {
        self.user.removeValueWithCompletionBlock { (err: NSError!, fire: Firebase!) -> Void in
            //println("user logged out.")
        }
    }
    
    func setName (name: String!) {
        username = name
        setUser()
    }
    
    func setLatitude (setlatitude: Double) {
        latitude = setlatitude
        //println(setlatitude)
        setUser()
    }
    
    func setLongitude (setlongitude: Double) {
        longitude = setlongitude
        //println(setlongitude)
        setUser()
    }
    
    func setRadius (setradius: Double!) {
        radius = setradius
        setUser()
    }
    
    func setUser () {
        user.setValue([
            "name": user.name,
            "latitude": latitude,
            "longitude": longitude,
            "radius": radius
        ])
    }
}

class AmbitUserList: NSObject {
    
    static let sharedInstance = AmbitUserList()
    let userRef = Firebase(url:"https://ambit.firebaseio.com/users")
    var users = [User]()
    var matchedUsers = [User]()
    var matchedNames = [String]()
    
    func getUsers() {
        //println("getting users...")
        userRef.observeEventType(FEventType.ChildAdded, withBlock: { (snapshot) in
            let name = snapshot.value["name"] as? String
            let latitude = snapshot.value["latitude"] as? Double
            let longitude = snapshot.value["longitude"] as? Double
            let radius = snapshot.value["radius"] as? Double
            
            let user = User(name: name, latitude: latitude, longitude: longitude, radius: radius)
            self.users += [user]
        })
        //println("users recieved")
    }
    
    func matchUsers() {
        //println("matching users...")
        for user in users {
            var dlatitude = user.latitude() - AmbitAuthHelper.sharedInstance.latitude
            var dlongitude = user.longitude() - AmbitAuthHelper.sharedInstance.longitude
            var distance = sqrt(dlatitude*dlatitude + dlongitude*dlongitude)
            if ((distance <= AmbitAuthHelper.sharedInstance.radius) && (distance <= user.radius())) {
                self.matchedUsers += [user]
            }
        }
        //println("users matched")
    }
}

class User : NSObject {
    var name_: String
    var latitude_: Double
    var longitude_: Double
    var radius_: Double
    
    convenience init(name: String?, latitude: Double?, longitude: Double?) {
        self.init(name: name, latitude: latitude, longitude: longitude)
    }
    
    init(name: String?, latitude: Double?, longitude: Double?, radius: Double?) {
        self.name_ = name!
        self.latitude_ = latitude!
        self.longitude_ = longitude!
        self.radius_ = radius!
    }
    
    func name() -> String! {
        return name_;
    }
    
    func latitude() -> Double! {
        return latitude_;
    }
    
    func longitude() -> Double! {
        return longitude_;
    }
    
    func radius() -> Double? {
        return radius_;
    }
}
