package im.limoo.emoji;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import im.limoo.emoji.model.Emoji;
import im.limoo.emoji.view.EmojiGridView;
import im.limoo.emoji.view.EmojiMultiAutoCompleteTextView;
import im.limoo.emoji.view.EmojisPopup;

public class EmojIconActions implements View.OnFocusChangeListener {

    private boolean useSystemEmoji = false;
    private EmojisPopup popup;
    private Context context;
    private View rootView;
    private ImageView emojiButton;
    private int KeyBoardIcon = R.drawable.ic_action_keyboard;
    private int SmileyIcons = R.drawable.smiley;
    private KeyboardListener keyboardListener;
    private List<EmojiMultiAutoCompleteTextView> emojiconEditTextList = new ArrayList<>();
    private EmojiMultiAutoCompleteTextView emojiconEditText;

    /**
     * Constructor
     *
     * @param ctx              The context of current activity.
     * @param rootView         The top most layout in your view hierarchy. The difference of this
     *                         view and the screen height will be used to calculate the keyboard
     *                         height.
     * @param emojiconEditText The Id of EditText.
     * @param emojiButton      The Id of ImageButton used to open Emoji
     */
    public EmojIconActions(Context ctx, View rootView, EmojiMultiAutoCompleteTextView emojiconEditText,
                           ImageView emojiButton) {
        this.emojiButton = emojiButton;
        this.context = ctx;
        this.rootView = rootView;
        addEmojiconEditTextList(emojiconEditText);
        this.popup = new EmojisPopup(rootView, ctx, useSystemEmoji);
    }

    /**
     * Constructor
     *
     * @param ctx              The context of current activity.
     * @param rootView         The top most layout in your view hierarchy. The difference of this
     *                         view and the screen height will be used to calculate the keyboard
     *                         height.
     * @param emojiconEditText The Id of EditText.
     * @param emojiButton      The Id of ImageButton used to open Emoji
     * @param iconPressedColor The color of icons on tab
     * @param tabsColor        The color of tabs background
     * @param backgroundColor  The color of emoji background
     */
    public EmojIconActions(Context ctx, View rootView, EmojiMultiAutoCompleteTextView emojiconEditText,
                           ImageView emojiButton, String iconPressedColor, String tabsColor,
                           String backgroundColor) {
        addEmojiconEditTextList(emojiconEditText);
        this.emojiButton = emojiButton;
        this.context = ctx;
        this.rootView = rootView;
        this.popup = new EmojisPopup(rootView, ctx, useSystemEmoji, iconPressedColor,
                tabsColor, backgroundColor);
    }

    public EmojIconActions(Context ctx, View rootView) {
        this.context = ctx;
        this.rootView = rootView;
        this.popup = new EmojisPopup(rootView, ctx, useSystemEmoji);
    }

    public void addEmojiconEditTextList(EmojiMultiAutoCompleteTextView... emojiconEditText) {
        Collections.addAll(emojiconEditTextList, emojiconEditText);
        for (EditText editText : emojiconEditText) {
            editText.setOnFocusChangeListener(this);
        }
    }

    public void setIconsIds(int keyboardIcon, int smileyIcon) {
        this.KeyBoardIcon = keyboardIcon;
        this.SmileyIcons = smileyIcon;
    }

    public void setUseSystemEmoji(boolean useSystemEmoji) {
        this.useSystemEmoji = useSystemEmoji;
        for (EmojiMultiAutoCompleteTextView editText : emojiconEditTextList) {
            editText.setUseSystemDefault(useSystemEmoji);
        }
        refresh();
    }


    private void refresh() {
        popup.updateUseSystemDefault(useSystemEmoji);
    }

    public void showEmojisWithoutKeyboard(final EmojiSelect emojiSelect){
        popup.setSize(WindowManager.LayoutParams.MATCH_PARENT,685);
        popup.setOnEmojiClickedListener(new EmojiGridView.OnEmojiClickedListener() {
            @Override
            public void onEmojiClicked(Emoji emoji) {
                if (emoji == null) {
                    return;
                }
                emojiSelect.onEmojiSelected(emoji.getShort_name());
                if (popup.isShowing()) popup.dismiss();

            }
        });

            if (!popup.isShowing()) popup.showAtBottom();
            else popup.dismiss();


    }

    public void ShowEmojIcon() {
        if (emojiconEditText == null)
            emojiconEditText = emojiconEditTextList.get(0);
        //Will automatically set size according to the soft keyboard size
        popup.setSizeForSoftKeyboard();

        //If the emoji popup is dismissed, change emojiButton to smiley icon
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                changeEmojiKeyboardIcon(emojiButton, SmileyIcons);
            }
        });

        //If the text keyboard closes, also dismiss the emoji popup
        popup.setOnSoftKeyboardOpenCloseListener(new EmojisPopup
                .OnSoftKeyboardOpenCloseListener() {

            @Override
            public void onKeyboardOpen(int keyBoardHeight) {
                if (keyboardListener != null)
                    keyboardListener.onKeyboardOpen();
            }

            @Override
            public void onKeyboardClose() {
                if (keyboardListener != null)
                    keyboardListener.onKeyboardClose();
                if (popup.isShowing())
                    popup.dismiss();
            }
        });

        //On emoji clicked, add it to edittext
        popup.setOnEmojiClickedListener(new EmojiGridView.OnEmojiClickedListener() {

            @Override
            public void onEmojiClicked(Emoji emoji) {
                if (emoji == null) {
                    return;
                }

                int start = emojiconEditText.getSelectionStart();
                int end = emojiconEditText.getSelectionEnd();
                if (start < 0) {
                    emojiconEditText.append(emoji.getShort_name());
                } else {
                    emojiconEditText.getText().replace(Math.min(start, end),
                            Math.max(start, end), emoji.getShort_name(), 0,
                            emoji.getShort_name().length());
                }
            }
        });

        //On backspace clicked, emulate the KEYCODE_DEL key event
        popup.setOnEmojiconBackspaceClickedListener(new EmojisPopup
                .OnEmojiconBackspaceClickedListener() {

            @Override
            public void onEmojiconBackspaceClicked(View v) {
                KeyEvent event = new KeyEvent(
                        0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                emojiconEditText.dispatchKeyEvent(event);
            }
        });

        // To toggle between text keyboard and emoji keyboard keyboard(Popup)
        showForEditText();
    }

    private void showForEditText() {

        emojiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (emojiconEditText == null)
                    emojiconEditText = emojiconEditTextList.get(0);
                //If popup is not showing => emoji keyboard is not visible, we need to show it
                if (!popup.isShowing()) {

                    //If keyboard is visible, simply show the emoji popup
                    if (popup.isKeyBoardOpen()) {
                        popup.showAtBottom();
                        changeEmojiKeyboardIcon(emojiButton, KeyBoardIcon);
                    }

                    //else, open the text keyboard first and immediately after that show the
                    // emoji popup
                    else {
                        emojiconEditText.setFocusableInTouchMode(true);
                        emojiconEditText.requestFocus();
                        final InputMethodManager inputMethodManager = (InputMethodManager)
                                context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(emojiconEditText, InputMethodManager
                                .SHOW_IMPLICIT);
                        popup.showAtBottomPending();
                        changeEmojiKeyboardIcon(emojiButton, KeyBoardIcon);
                    }
                }

                //If popup is showing, simply dismiss it to show the undelying text keyboard
                else {
                    popup.dismiss();
                }


            }
        });
    }

    public boolean isShowing() {
        return popup.isShowing();
    }

    public void closeEmojIcon() {
        if (popup != null && popup.isShowing())
            popup.dismiss();

    }

    private void changeEmojiKeyboardIcon(ImageView iconToBeChanged, int drawableResourceId) {
        iconToBeChanged.setImageResource(drawableResourceId);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            if (view instanceof EmojiMultiAutoCompleteTextView) {
                emojiconEditText = (EmojiMultiAutoCompleteTextView) view;
            }
        }
    }

    public void setKeyboardListener(KeyboardListener listener) {
        this.keyboardListener = listener;
    }

    public interface KeyboardListener {
        void onKeyboardOpen();

        void onKeyboardClose();
    }

    public interface EmojiSelect {
        void onEmojiSelected(String emoji);
    }

}
