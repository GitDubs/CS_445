//Ben Aston 
//CS 445, Monday/Wednesday night lecture
//Professor Garrision
//Date last updated: 2/7/2016

import java.io.Serializable;
public class Profile implements ProfileInterface, Serializable{
     private String name, aboutBlurb;
     private Set<Profile> following;
     
     //------------------------------------------------------------------------
     //constructor
     //------------------------------------------------------------------------
     public Profile(String name, String blurb){
         this.name = name;
         aboutBlurb = blurb;
         following = new Set<>();
     }
     
     //------------------------------------------------------------------------
     //setName: sets the profiles name to the parameter newName
     //------------------------------------------------------------------------
     public void setName(String newName) throws java.lang.IllegalArgumentException{
         if(newName == null)
             throw new java.lang.IllegalArgumentException();
         name = newName;
     }
     
     //------------------------------------------------------------------------
     //getName: returns this Profile's name 
     //------------------------------------------------------------------------
     public String getName(){
         return name;
     }
     
     //------------------------------------------------------------------------
     //setAbout: sets the profiles about text to the newAbout parameter
     //------------------------------------------------------------------------
     public void setAbout(String newAbout) throws java.lang.IllegalArgumentException{
         if(newAbout == null)
             throw new java.lang.IllegalArgumentException();
         aboutBlurb = newAbout;            
     }
     
     //------------------------------------------------------------------------
     //getAbout: returns this Profile's about text 
     //------------------------------------------------------------------------
     public String getAbout(){
         return aboutBlurb;
     }
     
     //------------------------------------------------------------------------
     //follow: adds the profile interface "other" to the following list and
     //returns true or false if succesful or not
     //------------------------------------------------------------------------
     public boolean follow(ProfileInterface other){
         checkInitialization();
         try{
            return following.add((Profile)other);
         }catch(SetFullException e){
             e.printStackTrace();
         }catch(IllegalArgumentException e){
             e.printStackTrace();
         }catch(Exception e){
             e.printStackTrace();
         }
            return false;
    }
     
    //------------------------------------------------------------------------
    //unfollow: removes the parameter "other" from the following set, and 
    //returns ture or false if successful or not
    //------------------------------------------------------------------------ 
    public boolean unfollow(ProfileInterface other){
        checkInitialization();
        
        if(other == null)
            return false;
        try{
            return following.remove((Profile)other);
        }catch(Exception e){
            return false;
        }    
    }
    
    //------------------------------------------------------------------------
    //following: returns a ProfileInterface [] with a max amount of elements
    //not greater than the parameter howMany
    //------------------------------------------------------------------------
    public ProfileInterface[] following(int howMany){
        ProfileInterface [] toReturn;
        Object [] temp;
        checkInitialization();
        
        temp = following.toArray();
        if(following.getCurrentSize() >= howMany){
            toReturn = new ProfileInterface [howMany];
            for(int i = 0; i < howMany; i++){
                toReturn[i] = (ProfileInterface) temp[i];
            }
            return toReturn;
        }
        
        toReturn = new ProfileInterface[temp.length];
        for(int i = 0; i < temp.length; i++){
            toReturn[i] = (ProfileInterface) temp[i];
        }
        return toReturn;
    }
    
    //------------------------------------------------------------------------
    //recommend: returns a profile that this profile does not follow, but
    //one of this profile's followed follows
    //------------------------------------------------------------------------
    public ProfileInterface recommend(){
        Object [] friends = following.toArray();
        ProfileInterface[]  friendsOfFriends;  
        
        //Looping through every friend in this profile's following set and 
        //and checking that friend's friends against this profile
        for(int i = 0; i < friends.length; i++){
          friendsOfFriends = ((ProfileInterface)(friends[i])).following(25);
          for(int j = 0; j < friends.length; j++){
              if(following.contains((Profile)friendsOfFriends[j]))
                  return friendsOfFriends[j];
             }
        }
        return null;
    }
    
    //------------------------------------------------------------------------
    //toString: returns a String representing the state of this Profile
    //------------------------------------------------------------------------
    public String toString(){
        return name + "\t" + aboutBlurb;
    }
    
    //------------------------------------------------------------------------
    //checkInitialization: makes sure that the following set has been i
    //initialized. *note* I had problems with it not being initialized when the 
    //object is restored from the ObjectInputStream, thus the existence of this
    //method
    //------------------------------------------------------------------------
    public void checkInitialization(){
        if(following == null)
            following = new Set<Profile>();
    }
}
