package controllers;

import java.util.ArrayList;
import play.mvc.*;
import models.*;

@With(Secure.class)
public class usr extends Controller
{
	public static void ls (
	) {
		render();
	}
}
