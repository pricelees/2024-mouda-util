document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('boardForm');
    form.addEventListener('submit', submitForm);
});

function submitForm(event) {
    event.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

    const team = document.querySelector('input[name="team"]').value;
    const author = document.querySelector('input[name="author"]').value;
    const content = document.querySelector('textarea[name="content"]').value;

    const data = {
        team: team,
        author: author,
        content: content
    };

    fetch('/boards', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            alert('성공적으로 제출되었습니다!');
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('제출에 실패했습니다.');
        });
}
