/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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

import java.util.Map;
import java.util.regex.Pattern;
import org.hawk.pattern.PatternMatcher;

/**
 *
 * @author Manoranjan Sahu
 */
public class Version implements Comparable<Version> {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d\\d\\.\\d\\d)\\.?(\\d?\\d?)");
    
    private static final Pattern MAJOR_VERSION_PATTERN = Pattern.compile("(\\d\\d)\\.(\\d\\d)");
    private String version;

    public static void main(String argsp[]) {
        Version ver = new Version("13.03.11");

    }

    public Version() {

    }

    public Version(String version) {
        this.version = version;
        this.splitVersions();
    }

    private boolean splitVersions() {
        Map<Integer, String> map = PatternMatcher.match(VERSION_PATTERN, this.getVersion());
        String majorStr = map.get(1);
        String minorStr = map.get(2);
        System.out.println(majorStr);
        System.out.println(minorStr);
        return true;
    }
    private MajorVersion majorVersion;

    private MinorVersion minorVersion;

    public MajorVersion getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(MajorVersion majorVersion) {
        this.majorVersion = majorVersion;
    }

    public MinorVersion getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(MinorVersion minorVersion) {
        this.minorVersion = minorVersion;
    }

    public static class MajorVersion extends Version {

        private Integer year;
        private Integer month;

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }
        
        public MajorVersion(String version) {
            super(version);
            Map<Integer, String> map = PatternMatcher.match(MAJOR_VERSION_PATTERN, this.getVersion());
            this.setYear(Integer.parseInt(map.get(1)));
            this.setMonth(Integer.parseInt(map.get(2)));
       
        }

        public int compareTo(MajorVersion otherVersion) {
            
            return this.getYear().compareTo(otherVersion.getYear()) == 0
                    ?
                        this.getMonth().compareTo(otherVersion.getMonth()) == 0
                            ?
                                0
                            :
                                this.getMonth().compareTo(otherVersion.getMonth())
                    :
                        this.getYear().compareTo(otherVersion.getYear());
        }

        @Override
        public String toString() {
            return "MajorVersion{" + this.getVersion() + '}';
        }
    }

    public static class MinorVersion extends Version {

        private Integer date;

        public Integer getDate() {
            return date;
        }

        public void setDate(Integer date) {
            this.date = date;
        }
        
        public MinorVersion(String version) {
            super(version);
            this.setDate(Integer.parseInt(this.getVersion()));
            
        }

        public int compareTo(MinorVersion otherVersion) {

            return this.getDate().compareTo(otherVersion.getDate());
        }

        @Override
        public String toString() {
            return "MinorVersion{" + this.getVersion() + '}';
        }

    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.version != null ? this.version.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Version other = (Version) obj;
        if ((this.version == null) ? (other.version != null) : !this.version.equals(other.version)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Version{" + "version=" + version + ", majorVersion=" + majorVersion + ", minorVersion=" + minorVersion + '}';
    }

    @Override
    public int compareTo(Version otherVersion) {
        return this.getMajorVersion().compareTo(otherVersion.getMajorVersion()) == 0
                ? this.getMinorVersion().compareTo(otherVersion.getMinorVersion()) == 0
                        ? 0
                        : this.getMinorVersion().compareTo(otherVersion.getMinorVersion())
                : this.getMajorVersion().compareTo(otherVersion.getMajorVersion());

    }
}
