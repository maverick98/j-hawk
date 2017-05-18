/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * 
 * 
 *
 * 
 */
package org.hawk.software;

import java.util.List;

/**
 *
 * @author Manoranjan Sahu
 */
public class Contributor {

    private ContributorTypeEnum authorType;
    private String name;
    private List<String> allContributors;
    private String contactNo;
    private String address;
    private String about;
    private String email;
    private String website;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    
    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public ContributorTypeEnum getAuthorType() {
        return authorType;
    }

    public void setAuthorType(ContributorTypeEnum authorType) {
        this.authorType = authorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAllContributors() {
        return allContributors;
    }

    public void setAllContributors(List<String> allContributors) {
        this.allContributors = allContributors;
    }

    public static enum ContributorTypeEnum {

        INDIVIDUAL, GROUP, COMPANY
    }
}
