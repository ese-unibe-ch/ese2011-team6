package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import models.UserDatabase;
import models.UserDatabase.*;

public class Register extends Controller
{
    public static void register()
    {
    	render();
    }

    public static void createNewUser(@Required String username, @Required String password) throws Throwable
    {
        if(validation.hasErrors())
        {
            flash.error("You have to provide an username and a password!");
            Register.register();
        }
        UserDatabase.getInstance().createUser(username, password);
    	Secure.authenticate(username, password, false);
    	Application.index();
    }
}