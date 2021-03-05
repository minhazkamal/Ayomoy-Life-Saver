# Ayomoy-Life-Saver

This is a project developed for the course *CSE 4402: Visual Programming Lab*.
The github repository comes with the source code and detailed documentation.
For the docuemntation please look at the folder *Javadoc* and for other documentation please
refer to the folder *Documentations*.

![Status](https://img.shields.io/badge/Status-Complete-brightgreen)
![IDE](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-blue)
![License](https://img.shields.io/badge/license-MIT-orange.svg)
![doc](https://img.shields.io/badge/Documentation-Javadoc-blue)
![UI](https://img.shields.io/badge/UI-JavaFX-brightgreen)
![Database](https://img.shields.io/badge/oracle%20-%23F00000.svg?&style=for-the-badge&logo=oracle&logoColor=white)



## Team Members:
* Minhaz Kamal - 180041231
* Chowdhury Mohammad Abdullah - 180041239
* Fairuz Shaiara - 180041240

## Demo: 
Here goes the brief demonstration  of the system for an easy understading.
### Welcome Page:
This is the welcome page. *Press to contuine.*<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/welcome.png)

## Sign in Page
*Sign In* Page. Provide Ceredentials to *Sign in* For. Pressing *Register* takes to the next page.<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/signIn.png)

## Register 
The username can not be changed later. Press *Next* to complete registration. Two types are possible in the dropdown button. <br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/register.png)

## Registration information 
Here the first for is for *Person* type user and the second is for *Organization* type user.<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/regfromPerson.png)

*Organizaton Type Registration:*<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/regfromOrg.png)

## User Dashboard
Here the user is presented with the personal nformations and other deriveed iformation on the main body of the page. *Updating the profile*, *Donor Mode* and *Donee Mode* are available for the person.<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/userDashboard.png)

## Update infroamtion 
Here the user can update all the fields except username and the *Blood Group*. For updating the *Blood Group*, person must contact *Admin* to change it manually. *Change* button will change the password.<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/updateInfo.png)

*Change Passowrd*<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/changepass.png)

## Donor Panel
This is the *Donor Mode* mentiond in the *User Dashboard* earlier. Here on the left side there are three flags. Their updation is related on the next page.<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/donorPanel.png)

## Donor Information
The Donor can set the *Paying Status* to *paid or NON-Paid* depending on the need. For *Eligibility*, the Donor can upload a test report here and later the *Admin* will review it. And For an *Eligable Donor*, s/he might be reluctant to donate blood for any personal issues. So the *Activ Staus* can be set to *Inactive* and the system will not this particular person in any search resul for blood donor.<br/><br/>

The lower portion is showing the previous review of reports that was done by *Admin*.<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/donorInfo.png)

## Donation Information
This is like a *clipboard* for the donor to store his donation innformaition. Filters are there to impose on the showed list.<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/donationInfo.png)

 He can also *Add new donation* by pressing the button.<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/addnewDonation.png)

## See Pending Request
Here the Donor will by default see the pending *Requests for blood donations* that match with his/ her *blood group*. Submitting a request is demonstrated on the *Donee Panel*.<br/><br/>
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/seependingrequest.png)

Also the *apply filer* button can extract other types of requests in case the user is interested.<br/><br/>


## Donee Panel
Here are three buttons for the Donee to make use of. 
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/doneePanel.png)

First of all the Donee can search for *Donor* or *Organization* which is fairly simple. Also, there is an option submit a donation request beforehand.  
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/newRequofDonee.png)

Submitting a new reqeust will enqueue it to the system and then it will automatically redirect to the *Previous Request* page which is just the list of all the requests that are in the system queued to be responded. 

## Organization Dashboard 
Here the *Update Profile* and *Use as Donee* buttons provide the same funcutionality as stated earlier in the person section. 
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/orgDashboard.png)

The *Use as Organizaton* button gives us the Organization Panel.
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/orgPanel.png)

Here *See Pending Request* is same as seen before on the *Person* section. The *Organization Information* is there for the updating of the Licencse periodically so that later the *Admin* can review them and asses their validity. 
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/orgInfo.png)

## Admin
This is a *hardcoded super profile* for the system administration and the main task of the profile is to update the eligbilty of the *Person* and the *Organazitaiton*. The *Admin Panel* has three buttons. *Change Password* is same as before. 
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/adminPanel.png)

Then comes the *Donor's List* where the *admin* will be able to review the test reports and give him/ her eligibilty flags. Also there is a *comment* section where *admin* can pass on some important remarks if necessary. The *view* button shows the test report that was updated by the *Person* earlier. 
![Alt text](https://github.com/minhazkamal/Ayomoy-Life-Saver/blob/main/src/sample/images/donorslistAdmin.png)

Same mechanism is there for the *Organizations List* where the *admin* looks into the Licence of the *Organization* and gives the eligibilty flags.
