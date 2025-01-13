package pokemongame.scene.battle

import pokemongame.pokemon.Category.STATUS
import pokemongame.pokemon.DefenseCurl
import pokemongame.pokemon.SuckerPunch
import pokemongame.pokemon.Tackle

fun DefenseCurl.visitExecuteMove(sceneState: BattleSceneState) =
    sceneState.defaultExecuteMove(battleStatChange = BattleStats(defense = BattleStat.one))

fun SuckerPunch.visitExecuteMove(sceneState: BattleSceneState) =
    with(sceneState.opposingTarget) {
        if (alreadyAttacked || chosenMove.category == STATUS) MoveExecutionResult.Failed
        else sceneState.defaultExecuteMove()
    }

fun Tackle.visitExecuteMove(sceneState: BattleSceneState) = sceneState.defaultExecuteMove()
