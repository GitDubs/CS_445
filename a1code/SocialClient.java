//Ben Aston 
//CS 445, Monday/Wednesday night lecture
//Professor Garrision
//Date last updated: 2/7/2016

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class SocialClient {
    private Set<Profile> masterList;
    
    public SocialClient(){
        
        if(!loadData())
            masterList = new Set<Profile>();
        menu();
        save();
    }
    
    //-------------------------------------------------------------------------
    //loadData: loads data from the defualt file "MasterList.bin",, returns a
    //boolean result indicating the loading success
    //-------------------------------------------------------------------------
    private boolean loadData(){
        String fileName = "MasterList.bin";
        try {
            ObjectInputStream restoreStream = new ObjectInputStream(new FileInputStream(fileName));
            masterList = (Set<Profile>) restoreStream.readObject();
        }
        catch(FileNotFoundException e) {
            System.err.println(fileName + " does not exist.");
            return false;
        }
        catch(IOException e) {
            System.err.println("Error restoring from " + fileName);
            return false;
        }
        catch(ClassNotFoundException e) {
            System.err.println("Error restoring from " + fileName);
            return false;
        }
        return true;
    }
    
    //-------------------------------------------------------------------------
    //loadData: loads data from the file passed to the method, returns a
    //boolean result indicating the loading success
    //-------------------------------------------------------------------------
    private boolean loadData(String fileName){
        try {
            ObjectInputStream restoreStream = new ObjectInputStream(new FileInputStream(fileName));
            masterList = (Set<Profile>) restoreStream.readObject();
        }
        catch(FileNotFoundException e) {
            System.err.println(fileName + " does not exist.");
            return false;
        }
        catch(IOException e) {
            System.err.println("Error restoring from " + fileName);
            return false;
        }
        catch(ClassNotFoundException e) {
            System.err.println("Error restoring from " + fileName);
            return false;
        }
        return true;
    }
    
    //-------------------------------------------------------------------------
    //save: saves the data to a file specified in the parameters
    //-------------------------------------------------------------------------
    private void save(String filename) {
        try {
            ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(filename));
            saveStream.writeObject(masterList);
        }
        catch(IOException e) {
            System.err.println("Something went wrong saving to " + filename);
            e.printStackTrace();
        }
    }
    
    //-------------------------------------------------------------------------
    //save: savesthe data to the defualt file "MasterList.bin"
    //-------------------------------------------------------------------------
    private void save() {
        String fileName = "MasterList.bin";
        try {
            ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(fileName));
            saveStream.writeObject(masterList);
        }
        catch(IOException e) {
            System.err.println("Something went wrong saving to " + fileName);
            e.printStackTrace();
        }
    }
    
    //-------------------------------------------------------------------------
    //menu: displays options to manipulate the profiles
    //-------------------------------------------------------------------------
    public void menu(){
        Profile tempProfile;
        ProfileInterface tempProfileInterface;
        ProfileInterface [] temp;
        Scanner input = new Scanner(System.in);
        int userInput;
        String name, aboutBlurb;
        
        System.out.println("Welcome to the Social Client");
        System.out.println("Select an action from the menu");
        System.out.println("1. List Profiles");
        System.out.println("2. Create Profile");
        System.out.println("3. Show Profile");
        System.out.println("4. Edit Profile");
        System.out.println("5. Follow");
        System.out.println("6. Unfollow");
        System.out.println("7. Suggest a follow");
        System.out.println("8. Exit");
        userInput = input.nextInt();
        input.nextLine();
        
        while(userInput != 8){
            if(userInput == 1){
                if(!masterList.isEmpty())
                    System.out.println(masterList);
                else
                    System.out.println("No profiles exist");
            }else if(userInput == 2){
                System.out.println("Enter the name of the new profile");
                name = input.nextLine();
                System.out.println("Enter the about text for the profile");
                aboutBlurb = input.nextLine();
                try{
                masterList.add(new Profile(name, aboutBlurb));
                }catch(SetFullException e){
                    System.err.println(e);
                }
            }else if(userInput == 3){
                tempProfile = masterList.peek();
                System.out.println(tempProfile);
                temp = tempProfile.following(4);
                if(temp.length == 0)
                    System.out.println("Not following to display");
                else
                    for(ProfileInterface p : temp){
                        System.out.println(p.getName() + "\t" + p.getAbout());
                    }
            }else if(userInput == 4){
                tempProfile = masterList.remove();
                System.out.println("Enter the new name for the profile");
                name = input.nextLine();
                System.out.println(name);
                tempProfile.setName(name);
                System.out.println("Enter the new about text for the profile");
                aboutBlurb = input.nextLine();
                tempProfile.setAbout(aboutBlurb);
            }else if(userInput == 5){
                tempProfile = masterList.peek();
                if(tempProfile.follow(masterList.peek()))
                    System.out.println(tempProfile + " now follows another profile");
            }else if(userInput == 6){
                tempProfile = masterList.peek();
                tempProfile.unfollow(masterList.peek());
            }else if(userInput == 7){
                tempProfile = masterList.peek();
                tempProfileInterface = tempProfile.recommend();
                if(tempProfileInterface != null){
                    System.out.println("Would you like to follow: ");
                    System.out.println(tempProfileInterface.getName() + "\t" + tempProfileInterface.getAbout());
                    System.out.println("1 for yes, anything else for no");
                    userInput = input.nextInt();
                    if(userInput == 1)
                        tempProfile.follow(tempProfileInterface);
                }else
                    System.out.println("No suggestions available");
                
            }
            System.out.println("Select an option from the menu");
            userInput = input.nextInt();
            input.nextLine();
            save();
        }
    }
    
    public static void main(String args[]){
        SocialClient client = new SocialClient();
    }
    
}
