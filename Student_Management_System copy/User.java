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
public class User {

	// CourseTitle, EnrolledStudents, ...

	// ID, UserName, Password, userType 
	private String name;
	private String id;
	
	public User(String name, String id) {
		this.name = name;
		this.id = id;
	}
	/** File Handling */
	public boolean checkUniqueID() {
		return false;
	}

	public boolean getID() {
		return false;
	}

	public void setID() {

	}



	/**	User Details */
	public String toString()
	{
		return ("Name: " + name + ", Id: " + id);
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	/** COURSE SECTION */
	public String getCourseTitle() {
		return title;
	}

	public boolean courseExist() {
		return false;
	}





}

public class Student extends User {
	
	public void addInCourse() {
		
	}

}


public class Admin extends User {
	
	public void createCourse() {

	}

}