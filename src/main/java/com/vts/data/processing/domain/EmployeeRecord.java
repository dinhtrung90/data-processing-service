package com.vts.data.processing.domain;

import lombok.*;
import org.springframework.batch.item.ItemCountAware;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import java.io.Serializable;

/**
 * Patient record from the input file
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRecord implements ItemCountAware {

	private static final long serialVersionUID = 1L;

    private int count;
    @NonNull
	private String sourceId;

    @NonNull
	private String firstName;

    @NonNull
	private String middleInitial;

    @NonNull
	private String lastName;

    @NonNull
    @Email(message = "please provide a valid email.")
	private String emailAddress;

    @NonNull
	private String phoneNumber;

	private String street;
	private String city;
	private String state;
	private String zip;

    @NonNull
	private String birthDate;

	private String action;
    @NonNull
	private String ssn;

    @Override
    public void setItemCount(int i) {
        this.count = i;
    }
}
