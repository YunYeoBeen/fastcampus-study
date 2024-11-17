package com.fastcampus.fastcampusstudy.common.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(createdBy: String) {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var regDtm: LocalDateTime? = null

    val createdBy: String = createdBy

    @LastModifiedDate
    @Column(nullable = false)
    @JsonIgnore
    var modDtm: LocalDateTime? = null

    var updateBy: String? = null

    fun update(updatedBy: String) {
        this.updateBy = updatedBy
    }
}
