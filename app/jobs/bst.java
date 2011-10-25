package jobs;

import play.jobs.*;
import play.test.*;
import models.*;

@OnApplicationStart
public class bst extends Job
{
	public void doJob (
	) {
		Fixtures.deleteAll();
		Fixtures.load("../test/data.yml");	/* XXX: path */
	}
}
