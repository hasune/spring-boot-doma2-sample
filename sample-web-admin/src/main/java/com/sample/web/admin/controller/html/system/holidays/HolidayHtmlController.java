package com.sample.web.admin.controller.html.system.holidays;

import static com.sample.domain.util.TypeUtils.toListType;
import static com.sample.web.base.WebConst.GLOBAL_MESSAGE;
import static com.sample.web.base.WebConst.MESSAGE_DELETED;

import com.sample.domain.dto.system.Holiday;
import com.sample.domain.dto.system.HolidayCriteria;
import com.sample.domain.service.system.HolidayService;
import com.sample.web.base.controller.html.AbstractHtmlController;
import com.sample.web.base.view.CsvView;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** 祝日管理 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/system/holidays")
@SessionAttributes(types = {SearchHolidayForm.class, HolidayForm.class})
@Slf4j
public class HolidayHtmlController extends AbstractHtmlController {

  @NonNull final HolidayFormValidator holidayFormValidator;

  @NonNull final HolidayService holidayService;

  @ModelAttribute("holidayForm")
  public HolidayForm holidayForm() {
    return new HolidayForm();
  }

  @ModelAttribute("searchHolidayForm")
  public SearchHolidayForm searchHolidayForm() {
    return new SearchHolidayForm();
  }

  @InitBinder("holidayForm")
  public void validatorBinder(WebDataBinder binder) {
    binder.addValidators(holidayFormValidator);
  }

  @Override
  public String getFunctionName() {
    return "A_HOLIDAY";
  }

  /**
   * 登録画面 初期表示
   *
   * @param form
   * @param model
   * @return
   */
  @PreAuthorize("hasAuthority('holiday:save')")
  @GetMapping("/new")
  public String newHoliday(@ModelAttribute("holidayForm") HolidayForm form, Model model) {
    if (!form.isNew()) {
      // SessionAttributeに残っている場合は再生成する
      model.addAttribute("holidayForm", new HolidayForm());
    }

    return "modules/system/holidays/new";
  }

  /**
   * 登録処理
   *
   * @param form
   * @param br
   * @param attributes
   * @return
   */
  @PreAuthorize("hasAuthority('holiday:save')")
  @PostMapping("/new")
  public String newHoliday(
      @Validated @ModelAttribute("holidayForm") HolidayForm form,
      BindingResult br,
      RedirectAttributes attributes) {
    // 入力チェックエラーがある場合は、元の画面にもどる
    if (br.hasErrors()) {
      setFlashAttributeErrors(attributes, br);
      return "redirect:/system/holidays/new";
    }

    // 入力値からDTOを作成する
    val inputHoliday = modelMapper.map(form, Holiday.class);

    // 登録する
    val createdHoliday = holidayService.create(inputHoliday);

    return "redirect:/system/holidays/show/" + createdHoliday.getId();
  }

  /**
   * 一覧画面 初期表示
   *
   * @param model
   * @return
   */
  @PreAuthorize("hasAuthority('holiday:read')")
  @GetMapping("/find")
  public String findHoliday(
      @ModelAttribute("searchHolidayForm") SearchHolidayForm form, Pageable pageable, Model model) {
    // 入力値から検索条件を作成する
    val criteria = modelMapper.map(form, HolidayCriteria.class);

    // 10件区切りで取得する
    val pages = holidayService.findAll(criteria, pageable);

    // 画面に検索結果を渡す
    model.addAttribute("pages", pages);

    return "modules/system/holidays/find";
  }

  /**
   * 検索結果
   *
   * @param form
   * @param br
   * @param attributes
   * @return
   */
  @PreAuthorize("hasAuthority('holiday:read')")
  @PostMapping("/find")
  public String findHoliday(
      @Validated @ModelAttribute("searchHolidayForm") SearchHolidayForm form,
      BindingResult br,
      RedirectAttributes attributes) {
    // 入力チェックエラーがある場合は、元の画面にもどる
    if (br.hasErrors()) {
      setFlashAttributeErrors(attributes, br);
      return "redirect:/system/holidays/find";
    }

    return "redirect:/system/holidays/find";
  }

  /**
   * 詳細画面
   *
   * @param holidayId
   * @param model
   * @return
   */
  @PreAuthorize("hasAuthority('holiday:read')")
  @GetMapping("/show/{holidayId}")
  public String showHoliday(@PathVariable Long holidayId, Model model) {
    val holiday = holidayService.findById(holidayId);
    model.addAttribute("holiday", holiday);
    return "modules/system/holidays/show";
  }

  /**
   * 編集画面 初期表示
   *
   * @param holidayId
   * @param form
   * @param model
   * @return
   */
  @PreAuthorize("hasAuthority('holiday:save')")
  @GetMapping("/edit/{holidayId}")
  public String editHoliday(
      @PathVariable Long holidayId, @ModelAttribute("holidayForm") HolidayForm form, Model model) {
    // セッションから取得できる場合は、読み込み直さない
    if (!hasErrors(model)) {
      // 1件取得する
      val holiday = holidayService.findById(holidayId);

      // 取得したDtoをFromに詰め替える
      modelMapper.map(holiday, form);
    }

    return "modules/system/holidays/new";
  }

  /**
   * 編集画面 更新処理
   *
   * @param form
   * @param br
   * @param holidayId
   * @param sessionStatus
   * @param attributes
   * @return
   */
  @PreAuthorize("hasAuthority('holiday:save')")
  @PostMapping("/edit/{holidayId}")
  public String editHoliday(
      @Validated @ModelAttribute("holidayForm") HolidayForm form,
      BindingResult br,
      @PathVariable Long holidayId,
      SessionStatus sessionStatus,
      RedirectAttributes attributes) {
    // 入力チェックエラーがある場合は、元の画面にもどる
    if (br.hasErrors()) {
      setFlashAttributeErrors(attributes, br);
      return "redirect:/system/holidays/edit/" + holidayId;
    }

    // 更新対象を取得する
    val holiday = holidayService.findById(holidayId);

    // 入力値を詰め替える
    modelMapper.map(form, holiday);

    // 更新する
    val updatedHoliday = holidayService.update(holiday);

    // セッションのholidayFormをクリアする
    sessionStatus.setComplete();

    return "redirect:/system/holidays/show/" + updatedHoliday.getId();
  }

  /**
   * 削除処理
   *
   * @param holidayId
   * @param attributes
   * @return
   */
  @PreAuthorize("hasAuthority('holiday:save')")
  @PostMapping("/remove/{holidayId}")
  public String removeHoliday(@PathVariable Long holidayId, RedirectAttributes attributes) {
    // 論理削除する
    holidayService.delete(holidayId);

    // 削除成功メッセージ
    attributes.addFlashAttribute(GLOBAL_MESSAGE, getMessage(MESSAGE_DELETED));

    return "redirect:/system/holidays/find";
  }

  /**
   * CSVダウンロード
   *
   * @param filename
   * @return
   */
  @PreAuthorize("hasAuthority('holiday:read')")
  @GetMapping("/download/{filename:.+\\.csv}")
  public ModelAndView downloadCsv(@PathVariable String filename) {
    // 全件取得する
    val holidays = holidayService.findAll(new HolidayCriteria(), Pageable.unpaged());

    // 詰め替える
    List<HolidayCsv> csvList = modelMapper.map(holidays.getContent(), toListType(HolidayCsv.class));

    // CSVスキーマクラス、データ、ダウンロード時のファイル名を指定する
    val view = new CsvView(HolidayCsv.class, csvList, filename);

    return new ModelAndView(view);
  }
}
