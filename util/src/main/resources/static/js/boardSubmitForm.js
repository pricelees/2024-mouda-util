document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('boardForm');
    if (form) {
        form.addEventListener('submit', submitForm);
    }
});

function submitForm(event) {
    event.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

    const team = document.querySelector('input[name="team"]').value;
    const author = document.querySelector('input[name="author"]').value;
    const content = document.querySelector('textarea[name="content"]').value;
    const isPublic = document.querySelector('input[name="isPublic"]').checked;

    const data = {
        team: team,
        author: author,
        content: content,
        isPublic: isPublic
    };

    fetch('/boards', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.status === 201) {
                alert('성공적으로 제출되었습니다!');
            } else {
                throw new Error('제출 실패! 내용을 다시 확인해주세요!');
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert(error.message);
        });
}
