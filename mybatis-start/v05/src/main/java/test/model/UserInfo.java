package test.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table user info
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class UserInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user info.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user info
     *
     * @mbggenerated
     */
    public UserInfo(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user info.id
     *
     * @return the value of user info.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }
}