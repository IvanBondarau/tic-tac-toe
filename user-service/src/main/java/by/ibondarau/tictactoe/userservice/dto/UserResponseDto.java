package by.ibondarau.tictactoe.userservice.dto;

import java.time.Instant;
import java.util.Objects;

public class UserResponseDto {
    private Integer id;
    private String email;
    private Instant created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UserResponseDto) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, created);
    }

    @Override
    public String toString() {
        return "UserResponseDto[" +
                "id=" + id + ", " +
                "email=" + email + ", " +
                "created=" + created + ']';
    }

}
