package net.chrisphilbin.todo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Todo subject cannot be blank.")
    @NonNull
    @Column(name = "subject", nullable = false)
    private String subject;

    @NotBlank(message = "Todo body cannot be blank.")
    @NonNull
    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "is_complete", nullable = false)
    private Boolean is_complete = false;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    
}
