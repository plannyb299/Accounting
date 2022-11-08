package com.example.financeservice.common;


import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@ToString
@MappedSuperclass
//@EntityListeners(AuditTrailListener.class)
public abstract class BaseEntity
  implements Serializable {
  private Long id;
  @Id
  @GeneratedValue
  @Column(name = "id")

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


}
