package ru.skypro.homework.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ads")
@Data
@NoArgsConstructor
public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @Column(nullable = false, length = 32)
    private String title;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 64)
    private String description;

    @Column(name = "image_path")
    private String image;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
}
