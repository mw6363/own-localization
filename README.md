# own-localization

locate your Mobile Device on your own platform like "Find my mobile"

- Mobile device(s) send location-Data (e.g. GPS-Data) periodically to your own server.

- Location Data are stored on your own server per device.

- A Web-GUI present the Device-Location-Data for your mobile device(s) in a nice manner


Mobile app  
- has a configuration Interface for the Mobile-Device-Name, server-Address and start/stop function for the background-process
- has a background-process which sends periodically location Data to the server.

Server 
- receives and stores the location Data send from the mobile Devices
- gives location Data to Web-GUI
- delete location Data, when required
- permits only configured and allowed Mobile-devices
- API-Access should be secured with an API-Key

Web-GUI
- present location-Data e.g. in a map for a device
- has an Admin-Interface to configure allowed mobile device -names.
- can request deletion of location Data per device
- Admin - Access needs protection (password)
