$(async function() {
    await thisUser();
});
async function thisUser() {
    fetch("http://localhost:8080/api/viewUser")
        .then(res => res.json())
        .then(data => {
            $('#headerUsername').append(data.username);
            let roles = data.roles.map(role => " " + role.role.substring(5));
            $('#headerRoles').append(roles);
            
            let user = `$(
            <tr>
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.surname}</td>
                <td>${data.password}</td>
                <td>${data.age}</td>
                <td>${data.email}</td>
                <td>${roles}</td>)`;
            $('#userPanelBody').append(user);
        })
}



