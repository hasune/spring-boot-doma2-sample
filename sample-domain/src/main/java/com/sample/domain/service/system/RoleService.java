package com.sample.domain.service.system;

import com.sample.domain.dto.system.Permission;
import com.sample.domain.dto.system.PermissionCriteria;
import com.sample.domain.dto.system.Role;
import com.sample.domain.dto.system.RoleCriteria;
import com.sample.domain.repository.system.PermissionRepository;
import com.sample.domain.repository.system.RoleRepository;
import com.sample.domain.service.BaseTransactionalService;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/** ロールサービス */
@RequiredArgsConstructor
@Service
public class RoleService extends BaseTransactionalService {

  @NonNull final RoleRepository roleRepository;

  @NonNull final PermissionRepository permissionRepository;

  /**
   * ロールを複数取得します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  public Page<Role> findAll(RoleCriteria criteria, Pageable pageable) {
    Assert.notNull(criteria, "criteria must not be null");
    return roleRepository.findAll(criteria, pageable);
  }

  /**
   * ロールを取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  public Optional<Role> findOne(RoleCriteria criteria) {
    Assert.notNull(criteria, "criteria must not be null");
    val role = roleRepository.findOne(criteria);

    role.ifPresent(
        r -> {
          val permissions = getPermissions();
          r.getPermissions().addAll(permissions);
        });

    return role;
  }

  /**
   * ロールを取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  public Role findById(final Long id) {
    Assert.notNull(id, "id must not be null");
    val role = roleRepository.findById(id);
    val permissions = getPermissions();
    role.getPermissions().addAll(permissions);
    return role;
  }

  /**
   * ロールを追加します。
   *
   * @param inputRole
   * @return
   */
  public Role create(final Role inputRole) {
    Assert.notNull(inputRole, "inputRole must not be null");
    return roleRepository.create(inputRole);
  }

  /**
   * ロールを更新します。
   *
   * @param inputRole
   * @return
   */
  public Role update(final Role inputRole) {
    Assert.notNull(inputRole, "inputRole must not be null");
    return roleRepository.update(inputRole);
  }

  /**
   * ロールを論理削除します。
   *
   * @return
   */
  public Role delete(final Long id) {
    Assert.notNull(id, "id must not be null");
    return roleRepository.delete(id);
  }

  private List<Permission> getPermissions() {
    return permissionRepository.findAll(new PermissionCriteria(), Pageable.unpaged()).getContent();
  }
}
