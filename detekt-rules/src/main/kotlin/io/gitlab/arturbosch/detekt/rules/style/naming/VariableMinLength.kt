package io.gitlab.arturbosch.detekt.rules.style.naming

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.resolve.calls.util.isSingleUnderscore

class VariableMinLength(config: Config = Config.empty) : Rule(config) {

	override val issue = Issue(javaClass.simpleName,
			Severity.Style,
			debt = Debt.FIVE_MINS)
	private val minimumVariableNameLength
			= valueOrDefault(MINIMUM_VARIABLE_NAME_LENGTH, DEFAULT_MINIMUM_VARIABLE_NAME_LENGTH)

	override fun visitProperty(property: KtProperty) {
		if (property.isSingleUnderscore) {
			return
		}

		if (property.identifierName().length < minimumVariableNameLength) {
			report(CodeSmell(
					issue.copy(description = "Variable names should be at least $minimumVariableNameLength characters long."),
					Entity.from(property)))
		}
	}

	companion object {
		const val MINIMUM_VARIABLE_NAME_LENGTH = "minimumVariableNameLength"
		private const val DEFAULT_MINIMUM_VARIABLE_NAME_LENGTH = 3
	}
}
