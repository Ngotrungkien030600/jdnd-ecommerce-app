import com.udacity.critter.user.EmployeeSkill;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a request to find available employees by skills and a specific date.
 * This class does not map directly to the database.
 */
public class EmployeeRequestDTO {
    private Set<EmployeeSkill> skills; // A set of skills required for the service
    private LocalDate date; // The date on which the service is needed

    /**
     * Get the set of skills required for the service.
     *
     * @return The set of skills required.
     */
    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    /**
     * Set the set of skills required for the service.
     *
     * @param skills The set of skills required.
     */
    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    /**
     * Get the date on which the service is needed.
     *
     * @return The date of the service.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set the date on which the service is needed.
     *
     * @param date The date of the service.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
