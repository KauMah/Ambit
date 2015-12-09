//
//  MapViewController.swift
//  FireChat-Swift
//
//  Created by Tim Lupo on 9/5/15.
//  Copyright (c) 2015 Firebase. All rights reserved.
//

import Foundation
import MapKit
import CoreLocation

class MapViewController : UIViewController, MKMapViewDelegate, CLLocationManagerDelegate {
    
    @IBOutlet weak var mapView: MKMapView!
    
    let locationManager = CLLocationManager()
    
    override func viewDidLoad() {
        self.locationManager.requestWhenInUseAuthorization()
        self.locationManager.delegate = self
        self.locationManager.desiredAccuracy = kCLLocationAccuracyBest
        self.locationManager.startUpdatingLocation()
        self.mapView.showsUserLocation = true
        //var location = CLLocation(latitude: 46.7667 as CLLocationDegrees, longitude: 23.58 as CLLocationDegrees)
        //addRadiusCircle(location)
    }
    
    func locationManager(manager: CLLocationManager!, didUpdateLocations locations: [AnyObject]!) {
        let location = locations.last as! CLLocation
        let center = CLLocationCoordinate2D(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude)
        AmbitAuthHelper.sharedInstance.setLatitude(Double(location.coordinate.latitude))
        AmbitAuthHelper.sharedInstance.setLongitude(Double(location.coordinate.longitude))
        addRadiusCircle(location)
        let region = MKCoordinateRegion(center: center, span: MKCoordinateSpan(latitudeDelta: 1, longitudeDelta: 1))
        self.mapView.setRegion(region, animated: true)
        self.locationManager.stopUpdatingLocation()
    }
    
    func addRadiusCircle(location: CLLocation){
        self.mapView.delegate = self
        var circle = MKCircle(centerCoordinate: location.coordinate, radius: 1000 as CLLocationDistance)
        self.mapView.addOverlay(circle)
    }
    
    func mapView(mapView: MKMapView!, rendererForOverlay overlay: MKOverlay!) -> MKOverlayRenderer! {
        if overlay is MKCircle {
            var circle = MKCircleRenderer(overlay: overlay)
            circle.strokeColor = UIColor.redColor()
            circle.fillColor = UIColor(red: 255, green: 0, blue: 0, alpha: 0.1)
            circle.lineWidth = 1
            return circle
        } else {
            return nil
        }
    }
    
    @IBAction func sliderEdit(sender: UISlider) {
        AmbitAuthHelper.sharedInstance.setRadius(Double(sender.value)/111111)
        var circleRadius = (Double(sender.value)/111111) as CLLocationDistance
    }
    
    func locationManager (manager: CLLocationManager, didFailWithError error: NSError) {
        println("Errors" + error.localizedDescription)
    }
}