package com.f2x.bank.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Identification Type is mandatory")
    @ManyToOne
    @JoinColumn(name = "identification_type_id")
    private DocumentType identificationType;

    @NotNull(message = "Identification Number is mandatory")
    @Column(name = "identification_number")
    private String identificationNumber;

    @NotNull(message = "First Name Type is mandatory")
    @Column(name = "first_name")
    private String firstName;
    
    @NotNull(message = "Last Nanme Type is mandatory")
    @Column(name = "last_name")
    private String lastName;
    
    @NotNull(message = "Email Type is mandatory")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Birth Date Type is mandatory")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}