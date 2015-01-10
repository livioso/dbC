
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.*;


@Entity
@Table(name="STUDENT")
@AttributeOverrides({
    @AttributeOverride(name="mFirstName", column=@Column(name="FIRSTNAME")),
    @AttributeOverride(name="mLastName", column=@Column(name="LASTNAME"))
})
public class Student extends AbstractPerson {

	public Student(String firstName, String lastName) {
		super(firstName, lastName);
		
		setStudentName(firstName + " " + lastName);
	}
	private int studentId;
	private String studentName;
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/*
	@ManyToOne
	@JoinColumn(name="college_id")
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	*/
}
