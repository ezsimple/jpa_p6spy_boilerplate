package newapp.domain.entity.pk;

import lombok.Data;

import java.io.Serializable;

@Data
public class CodeEntityPk implements Serializable { // 반드시 Serializable
  private String gid; // GroupId
  private String cid; // CodeId
}

