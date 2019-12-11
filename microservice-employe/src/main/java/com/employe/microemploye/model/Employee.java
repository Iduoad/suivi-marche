package com.employe.microemploye.model;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames= {"email"}))
public class Employee {

    @Id
    @GeneratedValue
    private Long id ;

    private String nom ;
    private String prenom ;
    private String email ;
    private String job ; // fonctionnaire, chef, directeur


    @OneToOne()
    @JoinColumn(name="service_id")
    private Service service ;

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  

    public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

	@Override
	public String toString() {
		return "Employee [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", job=" + job
				+ ", service=" + service + "]";
	}

   
}
