package controllers

import javax.inject.Inject

import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.mvc._

case class Answer(answer: String)

class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val answers = Seq("Answer 1", "Answer 2", "Answer 3")

  val answerForm = Form(
    mapping(
      "answer" -> nonEmptyText
    )(Answer.apply)(Answer.unapply))

  def index = Action {
    Ok(views.html.index(answers, answerForm))
  }

  def post = Action { implicit request =>
    answerForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(answers, formWithErrors))
      },
      data => {
        Ok(s"You chose answer: ${data.answer}")
      }
    )
  }

}
