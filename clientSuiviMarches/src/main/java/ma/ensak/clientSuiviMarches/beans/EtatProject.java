package ma.ensak.clientSuiviMarches.beans;

import java.util.Date;

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
public class EtatProject {

	int nbreEnCours=0;
	int nbreEnAttente=0;
	int nbreValide=0;
	int nbrenNonValide=0;
}
