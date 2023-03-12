/*************************************************************************
	Copyright © 2021 Konstantinidis Konstantinos

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at 

	      http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software 
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and 
	limitations under the License.
*************************************************************************/
import java.util.ArrayList;

public class Course {

	private String title;
	private ArrayList<User> user = new ArrayList<User>();
	
	public void adduser(User aUser)
	{
		User.add(aUser);
	}
	
	/* public String printDetails() {
		String details = "";
		
		details += "Course name: '"+ title + "' has the following students:" +"\n";
		
		if(user.isEmpty()) {
			details = "This course has no students enrolled.";
		}else {
			for(User user: user)
				details += user +"\n";
			details += "----------------------------------------------------------------------------------------------------------------------";
		}
		return details;
	} */
	
	public Course(String title) 
	{
		this.title = title;
	}
	
	
	public String getCourseTitle() {
		return title;
	}

	public boolean CourseExist() {
		return false;
	}

	public void createCourse() {

	}

	public void addInCourse() {
	}

}