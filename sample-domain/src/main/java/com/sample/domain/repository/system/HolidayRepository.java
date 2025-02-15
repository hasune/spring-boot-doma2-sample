package com.sample.domain.repository.system;

import static com.sample.domain.util.DomaUtils.createSelectOptions;
import static java.util.stream.Collectors.toList;

import com.sample.domain.dao.system.HolidayDao;
import com.sample.domain.dto.system.Holiday;
import com.sample.domain.dto.system.HolidayCriteria;
import com.sample.domain.exception.NoDataFoundException;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/** 祝日リポジトリ */
@RequiredArgsConstructor
@Repository
public class HolidayRepository {

  @NonNull final HolidayDao holidayDao;

  /**
   * 祝日を複数取得します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  public Page<Holiday> findAll(HolidayCriteria criteria, Pageable pageable) {
    val options = createSelectOptions(pageable).count();
    val data = holidayDao.selectAll(criteria, options, toList());
    return new PageImpl<>(data, pageable, options.getCount());
  }

  /**
   * 祝日を取得します。
   *
   * @param criteria
   * @return
   */
  public Optional<Holiday> findOne(HolidayCriteria criteria) {
    return holidayDao.select(criteria);
  }

  /**
   * 祝日を取得します。
   *
   * @return
   */
  public Holiday findById(final Long id) {
    return holidayDao
        .selectById(id)
        .orElseThrow(() -> new NoDataFoundException("holiday_id=" + id + " のデータが見つかりません。"));
  }

  /**
   * 祝日を追加します。
   *
   * @param inputHoliday
   * @return
   */
  public Holiday create(final Holiday inputHoliday) {
    holidayDao.insert(inputHoliday);

    return inputHoliday;
  }

  /**
   * 祝日を更新します。
   *
   * @param inputHoliday
   * @return
   */
  public Holiday update(final Holiday inputHoliday) {
    int updated = holidayDao.update(inputHoliday);

    if (updated < 1) {
      throw new NoDataFoundException("holiday_id=" + inputHoliday.getId() + " のデータが見つかりません。");
    }

    return inputHoliday;
  }

  /**
   * 祝日を論理削除します。
   *
   * @return
   */
  public Holiday delete(final Long id) {
    val holiday =
        holidayDao
            .selectById(id)
            .orElseThrow(() -> new NoDataFoundException("holiday_id=" + id + " のデータが見つかりません。"));

    int updated = holidayDao.delete(holiday);

    if (updated < 1) {
      throw new NoDataFoundException("holiday_id=" + id + " は更新できませんでした。");
    }

    return holiday;
  }
}
