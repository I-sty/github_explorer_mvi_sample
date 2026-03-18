package com.i_sz_app.githubexplorer.core.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.ExperimentalExtendedContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class, ExperimentalExtendedContracts::class)
fun <T : R, R> T.letIf(condition: Boolean, block: (T) -> R): R {

    //https://www.linkedin.com/posts/marcin-moskala_meet-my-friendly-coding-companion-letif-activity-7426550735233896448-XdWE/

    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        condition.holdsIn(block)
    }
    return if (condition) {
        block(this)
    } else {
        this
    }
}
