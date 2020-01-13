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
public class NbreDeProjetsValideParMois {

	int jan=0;
	int fev=0;
	int mar=0;
	int avr=0;
	int mai=0;
	int jun=0;
	int jul=0;
	int aou=0;
	int sep=0;
	int oct=0;
	int nov=0;
	int dec=0;
}
