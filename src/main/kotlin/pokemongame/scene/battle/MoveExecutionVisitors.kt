package pokemongame.scene.battle

import pokemongame.pokemon.Category.STATUS
import pokemongame.pokemon.SuckerPunch
import pokemongame.pokemon.Tackle

fun Tackle.visitExecuteMove(sceneState: BattleSceneState) = sceneState.defaultExecuteMove()

fun SuckerPunch.visitExecuteMove(sceneState: BattleSceneState): MoveExecutionResult? =
    with(sceneState) {
        if (opposingTarget.alreadyAttacked || opposingTarget.chosenMove.category == STATUS) {
            null
        } else {
            defaultExecuteMove()
        }
    }
