package io.lenur.aws.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "asset")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "s3_key")
    private String s3Key;

    @Column(name = "mime_type")
    private String mimeType;

    @Column
    private long size;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
}
