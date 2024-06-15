package com.example.thread.ui.screen.primary.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.thread.data.model.thread.ThreadType
import com.example.thread.data.model.user.UserResponse
import com.example.thread.data.viewmodel.TextData
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.button.ButtonVariant
import com.example.thread.ui.component.button.IconClickable
import com.example.thread.ui.component.button.TitleDescription
import com.example.thread.ui.component.common.ConfirmationAlert
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.common.rememberSingleImagePicker
import com.example.thread.ui.component.feed.FeedCard
import com.example.thread.ui.component.input.TextField
import com.example.thread.ui.component.layout.BottomSheet
import com.example.thread.ui.component.layout.ScaffoldBottomSheet
import com.example.thread.ui.component.layout.TabRowLayout
import com.example.thread.ui.component.layout.lazyThreadDetailsCard
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.text.TextCallOut
import com.example.thread.ui.component.text.TextHeadLine
import com.example.thread.ui.component.user.UserAvatar
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.followerlist.FollowerListDestination
import com.example.thread.ui.navigation.myprofile.MyProfileDestination
import com.example.thread.ui.screen.GlobalViewModelProvider
import okhttp3.internal.wait

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileHeader(
    threadNavController: ThreadNavController,
    user: UserResponse,
    viewModel: ProfileViewModel,
    myProfile: Boolean,
) {
    val showBottomSheet = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 12.dp)
            ) {
                TextHeadLine(text = "${user.user.firstName} ${user.user.lastName}")
                Spacer(modifier = Modifier.height(8.dp))
                TextBody(text = user.user.username)
                if (user.user.bio.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextBody(text = user.user.bio)
                }
            }
            UserAvatar(
                avatarURL = user.user.imageUrl,
                size = 64.dp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextBody(
            text = "${user.overview.follow.count} followers", // ViewModel data
            color = Color.Gray,
            modifier = Modifier.clickable {
                threadNavController.navigate(
                    "${FollowerListDestination.FOLLOWER_LIST.route}/${user.user.userId}"
                )
            })
        Spacer(modifier = Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (myProfile) {
                Button(
                    onClick = { showBottomSheet.value = true },
                    rounded = false,
                    modifier = Modifier.weight(1f),
                    buttonVariant = ButtonVariant.OUTLINED
                ) {
                    TextBody(text = "Edit profile", bold = true)
                }
            } else {
                Button(
                    onClick = {
                        viewModel.userData.onFollowUser()
                    },
                    rounded = false,
                    modifier = Modifier.weight(1f),
                    buttonVariant = if (user.overview.follow.isFollowing) ButtonVariant.OUTLINED else ButtonVariant.FILLED
                ) {
                    TextBody(
                        text = if (user.overview.follow.isFollowing) "Following" else "Follow",
                        color = if (user.overview.follow.isFollowing) Color.Black else Color.White,
                        bold = true
                    )
                }
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(true)
    val displaySaveConfirmation = remember {
        mutableStateOf(false)
    }

    // Edit Profile Screen
    ScaffoldBottomSheet(
        sheetState = sheetState,
        display = showBottomSheet,
        title = "Edit profile",
        onCancel = { dismiss ->
            // Just close the bottom sheet
            dismiss()
        },
        onDone = { dismiss ->
            if (viewModel.userData.isProfileInfoChanged()) {
                // Show a confirmation dialog if there are any changes
                displaySaveConfirmation.value = true
            } else {
                // Otherwise, just close the bottom sheet
                dismiss()
            }
        }
    ) { paddingValues, dismiss ->
        EditProfileScreen(
            modifier = Modifier.padding(paddingValues),
            user = user,
            viewModel = viewModel
        )

        // Confirmation dialog
        ConfirmationAlert(
            showDialog = displaySaveConfirmation,
            title = "Update your profile",
            text = "Are you sure to update your profile",
            onConfirmClick = {
                // Update the user profile to the server
                viewModel.userData.updateProfile()
                // Close the bottom sheet after updated
                dismiss()
            }
        )
    }
}

@Composable
fun ProfileScreen(
    threadNavController: ThreadNavController,
    targetUserId: Int,
    myProfile: Boolean = targetUserId == GlobalViewModelProvider.getCurrentUserId(),
) {
    val viewModel: ProfileViewModel = remember {
        ProfileViewModelProvider.getInstance(targetUserId)
    }

    val threadsData = viewModel.threadsData.data.collectAsState().value
    val userData = viewModel.userData.data.collectAsState().value

    val mainThreadsData =
        viewModel.mainThreadWithReplies.mainThreadsData.data.collectAsState().value
    val repliesData =
        viewModel.mainThreadWithReplies.repliesData.map { it.data.collectAsState().value }

    if (userData != null) {
        ThreadScaffold(topBar = {
            ThreadTopBar(
                threadNavController = threadNavController,
                actions = {
                    IconClickable(imageVector = Icons.Outlined.Notifications)
                    Spacer(modifier = Modifier.width(10.dp))
                    IconClickable(imageVector = Icons.Outlined.MoreHoriz, onClick = {
                        threadNavController.navigate(MyProfileDestination.SETTING.route)
                    })
                },
                showDivider = false,
                showBackButton = !myProfile
            )
        }) { paddingValues ->
            TabRowLayout(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                title = { ProfileHeader(threadNavController, userData, viewModel, myProfile) },
                tabTitles = listOf("Threads", "Replies"),
                initialPage = viewModel.currentPageIndex,
                onRefresh = { currentPage ->
                    viewModel.userData.retrieveUserData()
                    when (currentPage) {
                        0 -> {
                            viewModel.threadsData.getThreadsByUserId(
                                targetUserId,
                                ThreadType.POST.ordinal
                            )
                        }

                        1 -> {
                            viewModel.mainThreadWithReplies.retrieveRepliesData()
                        }
                    }
                }
            ) { pageIndex ->
                viewModel.currentPageIndex = pageIndex
                when (pageIndex) {
                    0 -> {
                        itemsIndexed(threadsData) { index, thread ->
                            FeedCard(
                                threadNavController = threadNavController,
                                threadData = thread,
                                onFeedCardClick = {
                                    threadNavController.navigateToThreadDetails(
                                        viewModel.threadsData,
                                        index
                                    )
                                },
                                onFavoriteClick = { isFavorited ->
                                    viewModel.threadsData.favoriteThread(
                                        isFavorited = isFavorited,
                                        index = index
                                    )
                                },
                                onReplyClick = {
                                    threadNavController.navigateToReplyToThread(
                                        viewModel.threadsData,
                                        index
                                    )
                                },
                                onDeleteConfirmed = {
                                    viewModel.threadsData.getThreadsByUserId(
                                        targetUserId,
                                        ThreadType.POST.ordinal
                                    )
                                }
                            )
                        }
                    }

                    1 -> {
                        if (mainThreadsData.isEmpty()) {
                            viewModel.mainThreadWithReplies.retrieveRepliesData()
                        } else {
                            mainThreadsData.forEachIndexed { index, mainThread ->
                                lazyThreadDetailsCard(
                                    threadNavController = threadNavController,
                                    onFavoriteToPostClick = { isFavorite ->
                                        viewModel.mainThreadWithReplies.favoritePost(
                                            isFavorite,
                                            index
                                        )
                                    },
                                    onPostCardClick = {
                                        threadNavController.navigateToThreadDetails(
                                            viewModel.mainThreadWithReplies.mainThreadsData,
                                            index
                                        )
                                    },
                                    onReplyToPostClick = {
                                        threadNavController.navigateToReplyToThread(
                                            viewModel.mainThreadWithReplies.mainThreadsData,
                                            index
                                        )
                                    },
                                    onFavoriteToReplyClick = { isFavorite: Boolean, replyIndex: Int ->
                                        viewModel.mainThreadWithReplies.favoriteThreadReply(
                                            isFavorite,
                                            index,
                                            replyIndex,
                                        )
                                    },
                                    onReplyToReplyClick = { replyIndex ->
                                        threadNavController.navigateToReplyToThread(
                                            viewModel.mainThreadWithReplies.repliesData[index],
                                            replyIndex
                                        )
                                    },
                                    onReplyCardClick = { replyIndex ->
                                        threadNavController.navigateToThreadDetails(
                                            viewModel.mainThreadWithReplies.repliesData[index],
                                            replyIndex
                                        )
                                    },
                                    thread = mainThread,
                                    threadReplies = repliesData[index],
                                    showBottomDivider = true,
                                    ableToClickPostCard = true,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    user: UserResponse,
    viewModel: ProfileViewModel,
) {
    var displayAvatarOptions = remember {
        mutableStateOf(false)
    }
    val displayEditBio = remember {
        mutableStateOf(false)
    }
    val displayEditName = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(null) {
        viewModel.userData.setDefaultEditedBio()
        viewModel.userData.setDefaultEditedFirstName()
        viewModel.userData.setDefaultEditedLastName()
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(height = 24.dp)
        Box(Modifier.fillMaxWidth()) {
            UserAvatar(
                modifier = Modifier.align(Alignment.Center),
                avatarURL = user.user.imageUrl,
                size = 128.dp,
                enableClick = true
            ) {
                displayAvatarOptions.value = true
            }
        }
        Spacer(height = 24.dp)
        Box(modifier = Modifier.background(Color.White, RoundedCornerShape(16.dp))) {
            Column(
                modifier = Modifier
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                TitleDescription(
                    title = "Name",
                    description = "${viewModel.userData.editedFirstName.value.text} ${viewModel.userData.editedLastName.value.text}",
                    modifier = Modifier.clickable { displayEditName.value = true }
                )
                Spacer(height = 20.dp)
                TitleDescription(
                    title = "Bio",
                    description = viewModel.userData.editedBio.value.text,
                    placeholder = "+ Write bio",
                    modifier = Modifier.clickable { displayEditBio.value = true }
                )
            }
        }
    }

    // Avatar options
    BottomSheet(
        display = displayAvatarOptions,
    ) { dismiss ->
        val imagePicker = rememberSingleImagePicker { viewModel.userData.updateUserImage(it) }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    imagePicker()
                    dismiss()
                },
                buttonVariant = ButtonVariant.FILLED,
                rounded = false,
                paddingValues = PaddingValues(16.dp)
            ) {
                TextCallOut(text = "Choose from library", color = Color.White)
            }
            Spacer(height = 8.dp)
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.userData.removeUserImage()
                    dismiss()
                },
                buttonVariant = ButtonVariant.OUTLINED,
                rounded = false,
                paddingValues = PaddingValues(16.dp)
            ) {
                TextCallOut(text = "Remove current picture", color = Color.Red)
            }
        }
    }

    EditBioScreen(
        viewModel = viewModel,
        display = displayEditBio,
    )

    EditNameScreen(
        viewModel = viewModel,
        display = displayEditName,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBioScreen(
    viewModel: ProfileViewModel,
    display: MutableState<Boolean>,
) {
    if (display.value) {
        val newBio = remember {
            TextData(viewModel.userData.editedBio.value.text)
        }
        // Bio edit screen
        ScaffoldBottomSheet(
            display = display,
            title = "Edit bio",
            onCancel = { dismiss ->
                // Just close the bottom sheet
                dismiss()
            },
            onDone = { dismiss ->
                // Update value to updated variable
                viewModel.userData.editedBio.setText(newBio.value)

                // Close the bottom sheet after updated
                dismiss()
            }
        ) { paddingValues, dismiss ->
            Box(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = paddingValues.calculateTopPadding() + 16.dp
                )
            ) {
                TextField(
                    value = newBio.value,
                    onValueChange = { newBio.setText(it) },
                    placeHolder = "Write a bio...",
                    label = "Bio",
                    backgroundColor = Color.White,
                    singleLine = false,
                    minLines = 5
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNameScreen(viewModel: ProfileViewModel, display: MutableState<Boolean>) {
    if (display.value) {
        val newFirstName = remember {
            TextData(viewModel.userData.editedFirstName.value.text)
        }
        val newLastName = remember {
            TextData(viewModel.userData.editedLastName.value.text)
        }
        // Edit name screen
        ScaffoldBottomSheet(
            display = display,
            title = "Edit name",
            onCancel = { dismiss ->
                // Just close the bottom sheet
                dismiss()
            },
            onDone = { dismiss ->
                // Update value to updated variables
                viewModel.userData.editedFirstName.setText(newFirstName.value)
                viewModel.userData.editedLastName.setText(newLastName.value)

                // Close the bottom sheet after updated
                dismiss()
            }
        ) { paddingValues, dismiss ->
            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = paddingValues.calculateTopPadding() + 16.dp
                )
            ) {
                TextField(
                    value = newFirstName.value,
                    onValueChange = { newFirstName.setText(it) },
                    placeHolder = "Your first name...",
                    label = "First name",
                    backgroundColor = Color.White,
                )
                Spacer(height = 16.dp)
                TextField(
                    value = newLastName.value,
                    onValueChange = { newLastName.setText(it) },
                    placeHolder = "Your last name...",
                    label = "Last name",
                    backgroundColor = Color.White,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(threadNavController = ThreadNavController(rememberNavController()), 1)
}
