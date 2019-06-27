# Report of process of the bearch app

### In short
The App I made in the last few weeks called Bearch is intended for people searching for a band or bands searching for a new member. In this App the user should be able to login and register, it also should have a filter function for the band and musicians. The bands and Members should all have an account and this account should be editable by the user self.
![Screenshot_1561634246](https://user-images.githubusercontent.com/47153142/60263353-c2372180-98e0-11e9-88af-3cddb170b6d0.png)
### Functionality
In this app there is a home screen with five buttons:
- My band(if user is in a band) / Request to : "some band"(if user requested to enter a band / Create Band(if user is new and not in a band or made a request to one
- Search Bands 
- Search Musicians
- Profile(if user is logged in) / Login (if user is not logged in)
- Log out (if user is logged in) / Register(if user is not logged in)

Those leads all to different screens:

- My band -> the profile page of your band where you as a member of the band are able to change the properties of the band
- Request to : "some band" -> Button is unclickable only shows that you're in await of response of the band
- Create band -> the page where the users are allowed to create their own band
- Search Bands -> the filter page where you can filter all the bands on their propperties -> result after filtering all the bands on those properties -> if one band is clicked from listview than show that bands profile
- Search Musicians -> the filter page where you can filter all the musicians on their propperties -> result after filtering all the musicians on those properties -> if one musician is clicked from listview than show that musicians profile
- Profile -> show profile of the current user, here they can edit their own properties
- Log out -> sets all the user values to zero so the user is logged out
- Login -> redirects to login page
- Register -> redirects to register page
