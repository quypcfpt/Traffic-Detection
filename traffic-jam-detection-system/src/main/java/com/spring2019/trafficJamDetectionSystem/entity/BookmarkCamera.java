package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Bookmark_Camera", schema = "dbo", catalog = "casptonetrafficjamdb")
public class BookmarkCamera {
    private int id;
    private Camera cameraByCameraId;
    private Bookmark bookmarkByBookmarkId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookmarkCamera that = (BookmarkCamera) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "camera_id", referencedColumnName = "id", nullable = false)
    public Camera getCameraByCameraId() {
        return cameraByCameraId;
    }

    public void setCameraByCameraId(Camera cameraByCameraId) {
        this.cameraByCameraId = cameraByCameraId;
    }

    @ManyToOne
    @JoinColumn(name = "bookmark_id", referencedColumnName = "id", nullable = false)
    public Bookmark getBookmarkByBookmarkId() {
        return bookmarkByBookmarkId;
    }

    public void setBookmarkByBookmarkId(Bookmark bookmarkByBookmarkId) {
        this.bookmarkByBookmarkId = bookmarkByBookmarkId;
    }
}
