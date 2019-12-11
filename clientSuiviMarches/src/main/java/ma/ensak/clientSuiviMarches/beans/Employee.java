package ma.ensak.clientSuiviMarches.beans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {

    
    Long id ;
    String nom ;
    String prenom ;
    String email ;
    String job ; // fonctionnaire, chef, directeur
    Service service ;
	@Override
	public String toString() {
		return "Employee [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", job=" + job
				+ ", service=" + service + "]";
	}
    

   
}
